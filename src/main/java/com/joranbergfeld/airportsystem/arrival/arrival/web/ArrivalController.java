package com.joranbergfeld.airportsystem.arrival.arrival.web;


import com.joranbergfeld.airportsystem.arrival.arrival.ArrivalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule/arrival")
public class ArrivalController {

    private final ArrivalService arrivalService;

    public ArrivalController(ArrivalService arrivalService) {
        this.arrivalService = arrivalService;
    }

    @GetMapping
    public List<ArrivalDto> getActiveArrivals() {
        return arrivalService.getActiveArrivals();
    }

    @PostMapping
    public ResponseEntity<ArrivalDto> createArrivalSchedule(@Valid @RequestBody ScheduleArrivalRequest request) {
        ArrivalDto arrivalDto = arrivalService.scheduleArrival(request.getAirlinerId(), request.getPlaneId(), request.getExpectedTime());
        return new ResponseEntity<>(arrivalDto, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/actual")
    public ArrivalDto receiveActualArrivalTime(@Valid @RequestBody ActualArrivalTimeRequest request, @PathVariable("id") Long id) {
        return arrivalService.setActualArrival(id, request.getActualArrivalTime());
    }
}
