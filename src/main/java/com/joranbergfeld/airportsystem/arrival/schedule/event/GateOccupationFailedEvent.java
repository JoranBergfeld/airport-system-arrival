package com.joranbergfeld.airportsystem.arrival.schedule.event;

public record GateOccupationFailedEvent (Long scheduleId, String reason) {

    @Override
    public String toString() {
        return "GateOccupationFailedEvent{" +
                "scheduleId=" + scheduleId +
                ", reason='" + reason + '\'' +
                '}';
    }
}
