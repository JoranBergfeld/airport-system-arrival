package com.joranbergfeld.airportsystem.arrival.persistence;

import com.joranbergfeld.airportsystem.arrival.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByActiveTrue();
}
