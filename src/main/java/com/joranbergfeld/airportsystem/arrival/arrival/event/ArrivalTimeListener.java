package com.joranbergfeld.airportsystem.arrival.arrival.event;

import com.joranbergfeld.airport_system.gate.client.api.GateControllerApi;
import com.joranbergfeld.airport_system.gate.client.model.Gate;
import com.joranbergfeld.airport_system.gate.client.model.OccupyGateRequest;
import com.joranbergfeld.airportsystem.arrival.event.ActualArrivalTimeRegisteredEvent;
import com.joranbergfeld.airportsystem.arrival.event.GateOccupationFailedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArrivalTimeListener  {
    private final Logger log = LoggerFactory.getLogger(ArrivalTimeListener.class);
    private final GateControllerApi gateClient;
    private final ApplicationEventPublisher applicationEventPublisher;


    public ArrivalTimeListener(GateControllerApi gateClient, ApplicationEventPublisher applicationEventPublisher) {
        this.gateClient = gateClient;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener
    public void groundControlListener(ActualArrivalTimeRegisteredEvent event) {
        log.info("Received event to trigger ground control flow: " + event.toString());
    }

    @EventListener
    public void gateReservationListener(ActualArrivalTimeRegisteredEvent event) {
        log.info("Received event to trigger reservation of gate: " + event.toString());
        List<Gate> allGates = gateClient.getAllGates();
        if (allGates.isEmpty()) {
            handleFailureToFindAvailableGate(event);
        }

        Gate gate = allGates.get(0);
        OccupyGateRequest request = new OccupyGateRequest();
        request.setOccupyingEntityId(event.scheduleId());
        try {
            Gate reservedGate = gateClient.occupyGate(gate.getId(), request);
            log.info("Reserved gate with ID: " + reservedGate.getId() + " for schedule with ID: " + event.scheduleId());
        } catch (Exception e) {
            handleFailureToOccupyGate(event);
        }
    }

    private void handleFailureToFindAvailableGate(ActualArrivalTimeRegisteredEvent event) {
        final String reason = "Could not find any gate. Sadface. Ask the developer to implement logic to handle this please.";
        emitGateOccupationFailedEvent(new GateOccupationFailedEvent(event.scheduleId(), reason));
        throw new RuntimeException(reason);
    }

    private void handleFailureToOccupyGate(ActualArrivalTimeRegisteredEvent event) {
        final String reason = "Could not occupy gate. Sadface. Ask the developer to implement logic to handle this please.";
        emitGateOccupationFailedEvent(new GateOccupationFailedEvent(event.scheduleId(), reason));
        throw new RuntimeException(reason);
    }

    private void emitGateOccupationFailedEvent(GateOccupationFailedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
