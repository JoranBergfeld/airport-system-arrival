package com.joranbergfeld.airportsystem.arrival.schedule;

import jakarta.persistence.*;


@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long airlinerId;
    private Long planeId;
    private Long requestedGateId;
    private Long assignedGateId;
    private long expectedAt;
    private long actualArrivalTime;
    private boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAirlinerId() {
        return airlinerId;
    }

    public void setAirlinerId(Long airlinerId) {
        this.airlinerId = airlinerId;
    }

    public Long getPlaneId() {
        return planeId;
    }

    public void setPlaneId(Long planeId) {
        this.planeId = planeId;
    }

    public Long getRequestedGateId() {
        return requestedGateId;
    }

    public void setRequestedGateId(Long gateId) {
        this.requestedGateId = gateId;
    }

    public Long getAssignedGateId() {
        return assignedGateId;
    }

    public void setAssignedGateId(Long assignedGateId) {
        this.assignedGateId = assignedGateId;
    }

    public long getExpectedAt() {
        return expectedAt;
    }

    public void setExpectedAt(long expectedAt) {
        this.expectedAt = expectedAt;
    }

    public long getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(long actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", airlinerId=" + airlinerId +
                ", planeId=" + planeId +
                ", expectedAt=" + expectedAt +
                ", actualArrivalTime=" + actualArrivalTime +
                ", active=" + active +
                '}';
    }
}
