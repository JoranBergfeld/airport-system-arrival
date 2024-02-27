package com.joranbergfeld.airportsystem.arrival.arrival;


import com.joranbergfeld.airportsystem.arrival.arrival.exception.ArrivalNotFoundException;
import com.joranbergfeld.airportsystem.arrival.arrival.web.ArrivalDto;
import com.joranbergfeld.airportsystem.arrival.arrival.exception.ArrivalSchedulingFailedException;
import com.joranbergfeld.airportsystem.arrival.schedule.Schedule;
import com.joranbergfeld.airportsystem.arrival.schedule.ScheduleService;
import com.joranbergfeld.airportsystem.arrival.schedule.exception.SchedulingFailedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArrivalService {

    private final ArrivalRepository repository;
    private final ScheduleService scheduleService;

    public ArrivalService(ArrivalRepository repository, ScheduleService scheduleService) {
        this.repository = repository;
        this.scheduleService = scheduleService;
    }

    public ArrivalDto scheduleArrival(Long airlinerId, Long planeId, Long gateId, long expectedTime) {
        Schedule schedule = scheduleService.scheduleArrival(airlinerId, planeId, gateId, expectedTime);
        Arrival arrival = new Arrival();
        arrival.setScheduleId(schedule.getId());
        arrival.setStatus(ArrivalStatus.SCHEDULED);
        Arrival savedArrival = repository.save(arrival);
        return createArrivalDtoFromArrivalAndSchedule(savedArrival, schedule);
    }

    public ArrivalDto setActualArrival(Long arrivalId, long actualArrivalTime) {
        Arrival arrival = repository.findById(arrivalId).orElseThrow(() -> new ArrivalNotFoundException(arrivalId));
        persistArrivalTime(actualArrivalTime, arrival);
        try {
            Schedule schedule = scheduleService.setActualArrivalAtGate(arrival.getScheduleId(), actualArrivalTime);
            persistArrivalStatus(arrival);
            return createArrivalDtoFromArrivalAndSchedule(arrival, schedule);
        } catch (SchedulingFailedException e) {
            throw new ArrivalSchedulingFailedException("Failed to schedule arrival with ID: " + arrivalId + " and actual arrival time: " + actualArrivalTime);
        }
    }

    private void persistArrivalStatus(Arrival arrival) {
        arrival.setStatus(ArrivalStatus.APPROACHING);
        repository.save(arrival);
    }

    private void persistArrivalTime(long actualArrivalTime, Arrival arrival) {
        arrival.setArrivingTime(actualArrivalTime);
        repository.save(arrival);
    }

    public List<ArrivalDto> getActiveArrivals() {
        return repository.findAllByActiveTrue().stream().map(this::createArrivalDtoFromArrival).collect(Collectors.toList());
    }

    private ArrivalDto createArrivalDtoFromArrival(Arrival arrival) {
        Schedule scheduleById = scheduleService.getScheduleById(arrival.getScheduleId());
        return createArrivalDtoFromArrivalAndSchedule(arrival, scheduleById);
    }

    private ArrivalDto createArrivalDtoFromArrivalAndSchedule(Arrival arrival, Schedule schedule) {
        return new ArrivalDto(arrival.getId(), schedule.getAirlinerId(), schedule.getPlaneId(), schedule.getRequestedGateId(), schedule.getAssignedGateId(), schedule.getExpectedAt(), schedule.getActualArrivalTime(), arrival.isActive());
    }
}
