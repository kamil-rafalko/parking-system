package com.kamilrafalko.parkingsystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class StopRequest {

    private final String licenseNumber;

    @JsonCreator
    public StopRequest(@JsonProperty("licenseNumber") String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}