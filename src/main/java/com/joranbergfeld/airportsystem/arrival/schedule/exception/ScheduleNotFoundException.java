package com.joranbergfeld.airportsystem.arrival.schedule.exception;

public class ScheduleNotFoundException extends RuntimeException {

    public ScheduleNotFoundException(Long scheduleId) {
        super("Schedule not found with id: " + scheduleId);
    }
}
