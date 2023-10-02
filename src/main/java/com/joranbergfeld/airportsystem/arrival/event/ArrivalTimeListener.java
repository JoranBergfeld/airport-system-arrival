package com.joranbergfeld.airportsystem.arrival.event;

import com.joranbergfeld.airport_system.gate.client.api.GateControllerApi;
import com.joranbergfeld.airport_system.gate.client.model.Gate;
import com.joranbergfeld.airport_system.gate.client.model.OccupyGateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArrivalTimeListener  {
    private final Logger log = LoggerFactory.getLogger(ArrivalTimeListener.class);
    private final GateControllerApi gateClient;

    public ArrivalTimeListener(GateControllerApi gateClient) {
        this.gateClient = gateClient;
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
            throw new RuntimeException("Could not find any gate. Sadface. Ask the developer to implement logic to handle this please.");
        }

        Gate gate = allGates.get(0);
        OccupyGateRequest request = new OccupyGateRequest();
        request.setOccupyingEntityId(event.scheduleId());
        gateClient.occupyGate(gate.getId(), request);
    }
}
