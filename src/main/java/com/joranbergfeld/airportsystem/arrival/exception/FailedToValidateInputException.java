package com.joranbergfeld.airportsystem.arrival.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FailedToValidateInputException extends RuntimeException {
    public FailedToValidateInputException(Exception e, Long entityId, String entityName) {
        super("Could not resolve entity " + entityName + " with id: " + entityId, e);
    }
}
