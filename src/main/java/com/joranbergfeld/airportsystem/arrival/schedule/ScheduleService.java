package com.joranbergfeld.airportsystem.arrival.schedule;

import com.joranbergfeld.airport_system.airliner.client.api.AirlinerControllerApi;
import com.joranbergfeld.airport_system.gate.client.api.GateControllerApi;
import com.joranbergfeld.airport_system.gate.client.model.Gate;
import com.joranbergfeld.airport_system.gate.client.model.OccupyGateRequest;
import com.joranbergfeld.airport_system.plane.client.api.PlaneControllerApi;
import com.joranbergfeld.airportsystem.arrival.event.ActualArrivalTimeRegisteredEvent;
import com.joranbergfeld.airportsystem.arrival.schedule.event.GateOccupationFailedEvent;
import com.joranbergfeld.airportsystem.arrival.exception.FailedToValidateInputException;
import com.joranbergfeld.airportsystem.arrival.schedule.persistence.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final AirlinerControllerApi airlinerClient;
    private final PlaneControllerApi planeClient;
    private final ScheduleRepository scheduleRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final GateControllerApi gateClient;
    private final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    public ScheduleService(AirlinerControllerApi airlinerClient, PlaneControllerApi planeClient, ScheduleRepository scheduleRepository, ApplicationEventPublisher applicationEventPublisher, GateControllerApi gateClient) {
        this.airlinerClient = airlinerClient;
        this.planeClient = planeClient;
        this.scheduleRepository = scheduleRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.gateClient = gateClient;
    }

    public Schedule scheduleArrival(Long airlinerId, Long planeId, long expectedTime) {
        ensurePlaneAndAirlinerAreRegistered(airlinerId, planeId);
        Schedule schedule = new Schedule();
        schedule.setAirlinerId(airlinerId);
        schedule.setPlaneId(planeId);
        schedule.setExpectedAt(expectedTime);
        return scheduleRepository.save(schedule);
    }

    private void ensurePlaneAndAirlinerAreRegistered(Long airlinerId, Long planeId) {
        ensurePlaneIsRegistered(planeId);
        ensureAirlinerIsRegistered(airlinerId);
    }

    private void ensurePlaneIsRegistered(Long planeId) {
        try {
            planeClient.getPlaneById(planeId);
        } catch (Exception e) {
            throw new FailedToValidateInputException(e, planeId, "Plane");
        }
    }

    private void ensureAirlinerIsRegistered(Long airlinerId) {
        try {
            airlinerClient.getAirlinerById(airlinerId);
        } catch (Exception e) {
            throw new FailedToValidateInputException(e, airlinerId, "Airliner");
        }
    }

    public Schedule setActualArrivalAtGate(Long scheduleId, long actualArrivalTime) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException(scheduleId));
        log.info("Obtaining trigger reservation for schedule with ID: " + scheduleId);
        List<Gate> allGates = gateClient.getAllGates();
        if (allGates.isEmpty()) {
            handleFailureToFindAvailableGate(scheduleId);
            return schedule;
        }

        Gate gate = allGates.get(0);
        OccupyGateRequest request = new OccupyGateRequest();
        request.setOccupyingEntityId(scheduleId);
        try {
            Gate reservedGate = gateClient.occupyGate(gate.getId(), request);
            log.info("Reserved gate with ID: " + reservedGate.getId() + " for schedule with ID: " + scheduleId);
        } catch (Exception e) {
            handleFailureToOccupyGate(scheduleId);
            return schedule;
        }

        // This logic is there for the downstream services to prep for gate occupation.
        // This is a "side effect" of setting the actual arrival time.
        applicationEventPublisher.publishEvent(new ActualArrivalTimeRegisteredEvent(scheduleId));

        // Finalize logic with settings the actual arrival time and persisting this.
        schedule.setActualArrivalTime(actualArrivalTime);
        return scheduleRepository.save(schedule);
    }

    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException(id));
    }

    public List<Schedule> getActiveSchedules() {
        return scheduleRepository.findAllByActiveTrue();
    }

    private void handleFailureToFindAvailableGate(long scheduleId) {
        final String reason = "Could not find any gate. Sadface. Ask the developer to implement logic to handle this please.";
        emitGateOccupationFailedEvent(new GateOccupationFailedEvent(scheduleId, reason));
        throw new RuntimeException(reason);
    }

    private void handleFailureToOccupyGate(long scheduleId) {
        final String reason = "Could not occupy gate. Sadface. Ask the developer to implement logic to handle this please.";
        emitGateOccupationFailedEvent(new GateOccupationFailedEvent(scheduleId, reason));
        throw new RuntimeException(reason);
    }

    private void emitGateOccupationFailedEvent(GateOccupationFailedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
