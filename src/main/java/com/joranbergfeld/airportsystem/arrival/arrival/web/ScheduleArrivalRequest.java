package com.joranbergfeld.airportsystem.arrival.arrival.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ScheduleArrivalRequest {

    @NotNull(message = "Plane ID is mandatory")
    private Long planeId;
    @NotNull(message = "Airliner ID is mandatory")
    private Long airlinerId;
    private Long requestedGateId;
    @Positive(message = "Expected time must be a positive number")
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

    public Long getRequestedGateId() {
        return requestedGateId;
    }

    public void setRequestedGateId(Long requestedGateId) {
        this.requestedGateId = requestedGateId;
    }

    public long getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(long expectedTime) {
        this.expectedTime = expectedTime;
    }
}
