package com.kamilrafalko.parkingsystem.rest;

import com.kamilrafalko.parkingsystem.domain.exceptions.IllegalParkingMeterOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalParkingMeterOperation.class)
    public ResponseEntity<ErrorResponse> handleIllegalParkingMeterOperation(RuntimeException exception) {
        ErrorResponse response = new ErrorResponse(exception.getClass().getName(), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
