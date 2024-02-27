package com.joranbergfeld.airportsystem.arrival.arrival.event;

import com.joranbergfeld.airportsystem.arrival.event.ActualArrivalTimeRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ArrivalTimeListener {
    private final Logger log = LoggerFactory.getLogger(ArrivalTimeListener.class);

    public ArrivalTimeListener() {
    }

    @EventListener
    public void groundControlListener(ActualArrivalTimeRegisteredEvent event) {
        log.info("Received event to trigger ground control flow: " + event.toString());
    }
}
