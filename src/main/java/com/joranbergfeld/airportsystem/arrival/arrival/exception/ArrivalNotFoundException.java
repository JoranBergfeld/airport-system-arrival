package com.joranbergfeld.airportsystem.arrival.arrival.exception;

public class ArrivalNotFoundException extends RuntimeException {
    public ArrivalNotFoundException(Long arrivalId) {
        super("Arrival not found with id: " + arrivalId);
    }
}
