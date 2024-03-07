package com.joranbergfeld.airportsystem.arrival.arrival.web;

public record ArrivalDto(Long arrivalId, Long scheduleId, Long airlinerId, Long planeId, Long requestedGateId, Long assignedGateId, long expectedAt, long actualArrivalTime, boolean active) {
    @Override
    public String toString() {
        return "ArrivalDto{" +
                "arrivalId=" + arrivalId +
                ", scheduleId=" + scheduleId +
                ", airlinerId=" + airlinerId +
                ", planeId=" + planeId +
                ", requestedGateId=" + requestedGateId +
                ", assignedGateId=" + assignedGateId +
                ", expectedAt=" + expectedAt +
                ", actualArrivalTime=" + actualArrivalTime +
                ", active=" + active +
                '}';
    }
}
