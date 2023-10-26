package com.joranbergfeld.airportsystem.arrival.arrival.web;

public record ArrivalDto(Long arrivalId, Long airlinerId, Long planeId, long expectedAt, long actualArrivalTime, boolean active) {
    @Override
    public String toString() {
        return "ArrivalDto{" +
                "arrivalId=" + arrivalId +
                ", airlinerId=" + airlinerId +
                ", planeId=" + planeId +
                ", expectedAt=" + expectedAt +
                ", actualArrivalTime=" + actualArrivalTime +
                ", active=" + active +
                '}';
    }
}
