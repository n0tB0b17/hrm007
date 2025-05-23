package com._7.hr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PositionAlreadyExistsException extends RuntimeException {
    public PositionAlreadyExistsException(String message) {
        super(message);
    }
}
