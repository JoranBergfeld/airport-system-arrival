package com.joranbergfeld.airportsystem.arrival.event;

public record GateOccupationFailedEvent (Long scheduleId, String reason) {

    @Override
    public String toString() {
        return "GateOccupationFailedEvent{" +
                "scheduleId=" + scheduleId +
                ", reason='" + reason + '\'' +
                '}';
    }
}
