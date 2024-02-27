package com.joranbergfeld.airportsystem.arrival.event;


//TODO May be relevant to rename this to something not-arrival related.
// This event is more generic. It's to prep airport operations for a plane to arrive in a certain location.
public record ActualArrivalTimeRegisteredEvent(Long scheduleId) {
    @Override
    public String toString() {
        return "ActualArrivalTimeRegisteredEvent{" +
                "scheduleId=" + scheduleId +
                '}';
    }
}
