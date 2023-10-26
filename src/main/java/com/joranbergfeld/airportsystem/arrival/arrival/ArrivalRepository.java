package com.joranbergfeld.airportsystem.arrival.arrival;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArrivalRepository extends JpaRepository<Arrival, Long> {
    List<Arrival> findAllByActiveTrue();
}
