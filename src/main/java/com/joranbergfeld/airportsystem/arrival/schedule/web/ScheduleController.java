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
    public List<Schedule> getAllScheduledArrivals() {
        return scheduleService.getActiveSchedules();
    }

}
