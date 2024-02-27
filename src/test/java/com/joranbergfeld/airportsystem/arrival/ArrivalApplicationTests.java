package com.joranbergfeld.airportsystem.arrival;

import com.joranbergfeld.airport_system.airliner.client.api.AirlinerControllerApi;
import com.joranbergfeld.airport_system.gate.client.api.GateControllerApi;
import com.joranbergfeld.airport_system.gate.client.model.Gate;
import com.joranbergfeld.airport_system.plane.client.api.PlaneControllerApi;
import com.joranbergfeld.airportsystem.arrival.arrival.Arrival;
import com.joranbergfeld.airportsystem.arrival.arrival.ArrivalRepository;
import com.joranbergfeld.airportsystem.arrival.arrival.web.ActualArrivalTimeRequest;
import com.joranbergfeld.airportsystem.arrival.arrival.web.ArrivalDto;
import com.joranbergfeld.airportsystem.arrival.arrival.web.ScheduleArrivalRequest;
import com.joranbergfeld.airportsystem.arrival.schedule.Schedule;
import com.joranbergfeld.airportsystem.arrival.schedule.persistence.ScheduleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArrivalApplicationTests {

    @LocalServerPort
    int port;

    @MockBean
    private GateControllerApi gateControllerApi;

    @MockBean
    private AirlinerControllerApi airlinerControllerApi;

    @MockBean
    private PlaneControllerApi planeControllerApi;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ArrivalRepository arrivalRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Should be able to create an arrival")
    void testArrival() {
        RestTemplate restTemplate = new RestTemplate();

        long planeId = 123L;
        long airlinerId = 234L;
        long expectedTime = 345L;

        ScheduleArrivalRequest scheduleArrivalRequest = new ScheduleArrivalRequest();
        scheduleArrivalRequest.setPlaneId(planeId);
        scheduleArrivalRequest.setAirlinerId(airlinerId);
        scheduleArrivalRequest.setExpectedTime(expectedTime);

        ResponseEntity<ArrivalDto> arrivalResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/schedule/arrival", scheduleArrivalRequest, ArrivalDto.class);

        Assertions.assertEquals(201, arrivalResponseEntity.getStatusCode().value(), "Should have 201 HTTP status code.");
        Assertions.assertNotNull(arrivalResponseEntity.getBody(), "Should have a non-null response body.");

        ArrivalDto arrivalDto = arrivalResponseEntity.getBody();
        Long createdArrivalId = arrivalDto.arrivalId();

        Assertions.assertNotNull(createdArrivalId, "Response body should have a non-null arrival ID.");
        Assertions.assertEquals(planeId, arrivalDto.planeId(), "Response body should have a plane ID of 123.");
        Assertions.assertEquals(airlinerId, arrivalDto.airlinerId(), "Response body should have an airliner ID of 234.");
        Assertions.assertEquals(expectedTime, arrivalDto.expectedAt(), "Response body should have an expected time of 345.");

        Arrival arrival = arrivalRepository.findById(createdArrivalId).orElseThrow();
        Assertions.assertNotNull(arrival, "Database should have a non-null arrival.");

        Schedule schedule = scheduleRepository.findById(arrival.getScheduleId()).orElseThrow();
        Assertions.assertNotNull(schedule, "Database should have a non-null schedule.");
        Assertions.assertEquals(expectedTime, schedule.getExpectedAt(), "Database schedule should have an expected time of 345.");
        Assertions.assertEquals(airlinerId, schedule.getAirlinerId(), "Database schedule should have an airliner ID of 234.");

        Gate gate = new Gate();
        gate.setName("A1");
        gate.setActive(true);
        gate.setSize(1);
        gate.setOccupied(false);
        gate.setId(1L);
        gate.setOccupyingEntityId(null);
        Mockito.when(gateControllerApi.getAllGates()).thenReturn(List.of(gate));
        Mockito.when(gateControllerApi.occupyGate(ArgumentMatchers.eq(1L), ArgumentMatchers.any())).thenReturn(gate);
        ActualArrivalTimeRequest actualArrivalTimeRequest = new ActualArrivalTimeRequest();
        ResponseEntity<ArrivalDto> actualArrivalResponse = restTemplate.postForEntity("http://localhost:" + port + "/schedule/arrival/" + arrival.getId() + "/actual", actualArrivalTimeRequest, ArrivalDto.class);

        Assertions.assertEquals(200, actualArrivalResponse.getStatusCode().value(), "Actual arrival response should have 200 HTTP status code.");
        Assertions.assertNotNull(actualArrivalResponse.getBody(), "Actual arrival response should have a non-null response body.");

    }
}
