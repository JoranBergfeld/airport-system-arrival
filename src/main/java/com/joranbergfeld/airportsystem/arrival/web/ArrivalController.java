package com.joranbergfeld.airportsystem.arrival.web;


import com.joranbergfeld.airportsystem.arrival.Schedule;
import com.joranbergfeld.airportsystem.arrival.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arrival/schedule")
public class ArrivalController {

    private final ScheduleService scheduleService;

    public ArrivalController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<Schedule> getAllScheduledArrivals() {
        return scheduleService.getActiveArrivals();
    }

    @PostMapping
    public Schedule scheduleArrival(@Valid @RequestBody ScheduleArrivalRequest request) {
        return scheduleService.scheduleArrival(request.getAirlineId(), request.getPlaneId(), request.getExpectedTime());
    }

    @PostMapping("/{id}/actual")
    public Schedule receiveActualArrivalTime(@Valid @RequestBody ActualArrivalTimeRequest request, @PathVariable("id") Long id) {
        return scheduleService.setActualArrival(id, request.getActualArrivalTime());
    }
}
