package com.kamilrafalko.parkingsystem.domain.exceptions;

public class MaxHoursLimitExceededException extends RuntimeException {
    public MaxHoursLimitExceededException(String message) {
        super(message);
    }
}
