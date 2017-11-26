package com.kamilrafalko.parkingsystem.domain.exceptions;

public class IllegalParkingMeterOperation extends RuntimeException {
    public IllegalParkingMeterOperation(String message) {
        super(message);
    }
}
