package com.joranbergfeld.airportsystem.arrival.schedule.exception;

public class SchedulingFailedException extends RuntimeException {
    public SchedulingFailedException(String message) {
        super(message);
    }
}
