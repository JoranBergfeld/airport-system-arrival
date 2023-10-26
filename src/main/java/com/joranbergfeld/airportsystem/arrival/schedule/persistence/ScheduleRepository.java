package com.joranbergfeld.airportsystem.arrival.schedule.persistence;

import com.joranbergfeld.airportsystem.arrival.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByActiveTrue();
}
