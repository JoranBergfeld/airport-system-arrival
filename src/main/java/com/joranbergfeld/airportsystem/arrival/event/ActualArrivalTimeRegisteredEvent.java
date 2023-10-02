package com.joranbergfeld.airportsystem.arrival.event;

public record ActualArrivalTimeRegisteredEvent(Long scheduleId) {
    @Override
    public String toString() {
        return "ActualArrivalTimeRegisteredEvent{" +
                "scheduleId=" + scheduleId +
                '}';
    }
}
