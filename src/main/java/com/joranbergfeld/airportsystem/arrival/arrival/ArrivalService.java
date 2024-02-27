package com.joranbergfeld.airportsystem.arrival.arrival;


import com.joranbergfeld.airport_system.airliner.client.api.AirlinerControllerApi;
import com.joranbergfeld.airport_system.plane.client.api.PlaneControllerApi;
import com.joranbergfeld.airportsystem.arrival.arrival.exception.ArrivalNotFoundException;
import com.joranbergfeld.airportsystem.arrival.arrival.web.ArrivalDto;
import com.joranbergfeld.airportsystem.arrival.exception.ArrivalSchedulingFailedException;
import com.joranbergfeld.airportsystem.arrival.schedule.Schedule;
import com.joranbergfeld.airportsystem.arrival.schedule.ScheduleService;
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

    // TODO Implement "state" of schedule.
    // If scheduling failed, there should be a retry mechanism.
    // Example: Every 5 minutes, try to schedule again if the flight is in expectation.
    // If the flight is closing into the airport, the timing of this job should be more frequent.
    // Note to self: Probably should detect this logic from arrival. Or have the arrival logic be responsible for the retry.
    // Reasoning for logic being in the arrival is that arrival and departure may need this logic, whereas ground movement and departures will not be as "retry" prone.
    // Ground movements just look at available gates.
    // Departures look at available runways, not gates.
    // Only arrivals look at available runways and gates.
    // Runway retry mechanism may be shared between departures and arrivals?
    // K fuck it. Arrivals do the retry mechanism

    public ArrivalDto scheduleArrival(Long airlinerId, Long planeId, long expectedTime) {
        Schedule schedule = scheduleService.scheduleArrival(airlinerId, planeId, expectedTime);
        Arrival arrival = new Arrival();
        arrival.setScheduleId(schedule.getId());
        arrival.setStatus(ArrivalStatus.SCHEDULED);
        Arrival savedArrival = repository.save(arrival);
        return createArrivalDtoFromArrivalAndSchedule(savedArrival, schedule);
    }

    public ArrivalDto setActualArrival(Long arrivalId, long actualArrivalTime) {
        Arrival arrival = repository.findById(arrivalId).orElseThrow(() -> new ArrivalNotFoundException(arrivalId));
        persistArrivalTime(actualArrivalTime, arrival);
        Schedule schedule = scheduleService.setActualArrivalAtGate(arrival.getScheduleId(), actualArrivalTime);
        // Check if schedule updated correctly
        // TODO this logic should be a bit cleaner
        if (schedule.getActualArrivalTime() == actualArrivalTime) {
            persistArrivalStatus(arrival);
            return createArrivalDtoFromArrivalAndSchedule(arrival, schedule);
        }

        throw new ArrivalSchedulingFailedException("Failed to schedule arrival with ID: " + arrivalId + " and actual arrival time: " + actualArrivalTime);
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
        return new ArrivalDto(arrival.getId(), schedule.getAirlinerId(), schedule.getPlaneId(), schedule.getExpectedAt(), schedule.getActualArrivalTime(), arrival.isActive());
    }
}
