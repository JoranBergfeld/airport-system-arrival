package com.joranbergfeld.airportsystem.arrival.arrival.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ArrivalSchedulingFailedException extends RuntimeException {
    public ArrivalSchedulingFailedException(String message) {
        super(message);
    }
}
