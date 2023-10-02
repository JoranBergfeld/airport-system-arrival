package com.joranbergfeld.airportsystem.arrival;

import com.joranbergfeld.airport_system.airliner.client.api.AirlinerControllerApi;
import com.joranbergfeld.airport_system.plane.client.api.PlaneControllerApi;
import com.joranbergfeld.airportsystem.arrival.event.ActualArrivalTimeRegisteredEvent;
import com.joranbergfeld.airportsystem.arrival.exception.FailedToValidateInputException;
import com.joranbergfeld.airportsystem.arrival.exception.ScheduleNotFoundException;
import com.joranbergfeld.airportsystem.arrival.persistence.ScheduleRepository;
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
    private Logger log = LoggerFactory.getLogger(ScheduleService.class);

    public ScheduleService(AirlinerControllerApi airlinerClient, PlaneControllerApi planeClient, ScheduleRepository scheduleRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.airlinerClient = airlinerClient;
        this.planeClient = planeClient;
        this.scheduleRepository = scheduleRepository;
        this.applicationEventPublisher = applicationEventPublisher;
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

    public Schedule setActualArrival(Long scheduleId, long actualArrivalTime) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException(scheduleId));
        schedule.setActualArrivalTime(actualArrivalTime);
        applicationEventPublisher.publishEvent(new ActualArrivalTimeRegisteredEvent(scheduleId));
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getActiveArrivals() {
        return scheduleRepository.findAllByActiveTrue();
    }
}
