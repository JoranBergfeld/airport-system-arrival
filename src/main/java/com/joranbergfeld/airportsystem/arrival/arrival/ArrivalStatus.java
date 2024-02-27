package com.joranbergfeld.airportsystem.arrival.arrival;

public enum ArrivalStatus {
    UNKNOWN,
    SCHEDULED,
    FAILED_TO_OBTAIN_GATE_OCCUPATION_WHILE_APPROACHING,
    APPROACHING,
    LANDED,

    // Arrival is no longer relevant for airport
    CANCELLED,
    DIVERTED
}
