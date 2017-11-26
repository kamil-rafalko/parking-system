package com.kamilrafalko.parkingsystem.domain.dto;

import lombok.Getter;

@Getter
public class CarState {
    private final boolean onParking;

    public CarState(boolean onParking) {
        this.onParking = onParking;
    }
}
