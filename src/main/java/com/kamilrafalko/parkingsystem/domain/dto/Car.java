package com.kamilrafalko.parkingsystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Car {

    private final String licenseNumber;

    @JsonCreator
    public Car(@JsonProperty("licenseNumber") String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
