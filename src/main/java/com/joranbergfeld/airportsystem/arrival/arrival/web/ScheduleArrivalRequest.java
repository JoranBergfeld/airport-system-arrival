package com.joranbergfeld.airportsystem.arrival.arrival.web;

public class ScheduleArrivalRequest {
    private Long planeId;
    private Long airlinerId;
    private long expectedTime;

    public Long getPlaneId() {
        return planeId;
    }

    public void setPlaneId(Long planeId) {
        this.planeId = planeId;
    }

    public Long getAirlinerId() {
        return airlinerId;
    }

    public void setAirlinerId(Long airlinerId) {
        this.airlinerId = airlinerId;
    }

    public long getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(long expectedTime) {
        this.expectedTime = expectedTime;
    }
}
