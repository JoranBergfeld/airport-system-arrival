package com.joranbergfeld.airportsystem.arrival.schedule.web;

import com.joranbergfeld.airportsystem.arrival.schedule.Schedule;
import com.joranbergfeld.airportsystem.arrival.schedule.ScheduleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getActiveSchedules();
    }

    @GetMapping("/expected")
    public List<Schedule> getAllSchedulesWithExpectedTime() {
        return scheduleService.getAllExpectedArrivals();
    }

    @GetMapping("/assigned")
    public List<Schedule> getAllSchedulesWithAssignedGates() {
        return scheduleService.getAllSchedulesWithAssignedGates();
    }

}
