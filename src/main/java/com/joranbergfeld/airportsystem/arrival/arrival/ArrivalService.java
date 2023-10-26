package com.joranbergfeld.airportsystem.arrival.arrival;


import com.joranbergfeld.airport_system.airliner.client.api.AirlinerControllerApi;
import com.joranbergfeld.airport_system.plane.client.api.PlaneControllerApi;
import com.joranbergfeld.airportsystem.arrival.arrival.exception.ArrivalNotFoundException;
import com.joranbergfeld.airportsystem.arrival.arrival.web.ArrivalDto;
import com.joranbergfeld.airportsystem.arrival.schedule.Schedule;
import com.joranbergfeld.airportsystem.arrival.schedule.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArrivalService {

    private final ArrivalRepository repository;
    private final AirlinerControllerApi airlinerClient;
    private final PlaneControllerApi planeClient;

    private final ScheduleService scheduleService;

    public ArrivalService(ArrivalRepository repository, AirlinerControllerApi airlinerClient, PlaneControllerApi planeClient, ScheduleService scheduleService) {
        this.repository = repository;
        this.airlinerClient = airlinerClient;
        this.planeClient = planeClient;
        this.scheduleService = scheduleService;
    }

    public ArrivalDto scheduleArrival(Long airlinerId, Long planeId, long expectedTime) {
        Schedule schedule = scheduleService.scheduleArrival(airlinerId, planeId, expectedTime);
        Arrival arrival = new Arrival();
        arrival.setScheduleId(schedule.getId());
        Arrival savedArrival = repository.save(arrival);
        return createArrivalDtoFromArrivalAndSchedule(savedArrival, schedule);
    }

    public ArrivalDto setActualArrival(Long arrivalId, long actualArrivalTime) {
        Arrival arrival = repository.findById(arrivalId).orElseThrow(() -> new ArrivalNotFoundException(arrivalId));
        Schedule schedule = scheduleService.setActualArrival(arrival.getScheduleId(), actualArrivalTime);
        return createArrivalDtoFromArrivalAndSchedule(arrival, schedule);
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
