package com.joranbergfeld.airportsystem.arrival.schedule.event;

import com.joranbergfeld.airportsystem.arrival.event.GateOccupationFailedEvent;
import com.joranbergfeld.airportsystem.arrival.schedule.Schedule;
import com.joranbergfeld.airportsystem.arrival.schedule.ScheduleNotFoundException;
import com.joranbergfeld.airportsystem.arrival.schedule.persistence.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GateOccupiedFailedListener {
    private final Logger log = LoggerFactory.getLogger(GateOccupiedFailedListener.class);
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ScheduleRepository repository;

    public GateOccupiedFailedListener(ApplicationEventPublisher applicationEventPublisher, ScheduleRepository repository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.repository = repository;
    }

    @EventListener
    public void listenForGateOccupationFailedEvent(GateOccupationFailedEvent event) {
        Schedule schedule = repository.findById(event.scheduleId()).orElseThrow(() -> new ScheduleNotFoundException(event.scheduleId()));
        schedule.setActualArrivalTime(-1);
        repository.save(schedule);
        log.info("Removed actual arrival time from schedule with ID: " + event.scheduleId() + " because gate occupation failed.");
    }
}
