package com.joranbergfeld.airportsystem.arrival.arrival;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArrivalRetryService {

    private final Logger log = LoggerFactory.getLogger(ArrivalRetryService.class);
    private final ArrivalRepository arrivalRepository;
    private final ArrivalService arrivalService;

    public ArrivalRetryService(ArrivalRepository arrivalRepository, ArrivalService arrivalService) {
        this.arrivalRepository = arrivalRepository;
        this.arrivalService = arrivalService;
    }

    @Scheduled(fixedRateString = "${app.arrival.retry.schedule-rate}", initialDelayString = "${app.arrival.retry.schedule-initial-delay}")
    public void retryFailedArrivals() {
        log.debug("Retrying failed arrivals");
        List<Arrival> allByStatus = arrivalRepository.findAllByStatus(ArrivalStatus.FAILED_TO_OBTAIN_GATE_OCCUPATION_WHILE_APPROACHING);
        allByStatus.forEach(arrival -> {
            log.debug("Retrying arrival with id {}", arrival.getId());
            try {
                arrivalService.setActualArrival(arrival.getId(), arrival.getArrivingTime());
            } catch (RuntimeException e) {
                log.error("Failed to retry arrival with id {}", arrival.getId(), e);
            }
        });
        log.debug("Finished retrying failed arrivals");
    }
}
