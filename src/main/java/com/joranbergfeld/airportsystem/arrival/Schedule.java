package com.joranbergfeld.airportsystem.arrival;

import jakarta.persistence.*;


@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long airlinerId;
    private Long planeId;
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
