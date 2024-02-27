package com.joranbergfeld.airportsystem.arrival.arrival;

import jakarta.persistence.*;

@Entity
@Table(name = "arrival")
public class Arrival {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long scheduleId;
    private long arrivingTime;
    private boolean active = true;
    private ArrivalStatus status = ArrivalStatus.UNKNOWN;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public long getArrivingTime() {
        return arrivingTime;
    }

    public void setArrivingTime(long arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrivalStatus getStatus() {
        return status;
    }

    public void setStatus(ArrivalStatus status) {
        this.status = status;
    }
}
