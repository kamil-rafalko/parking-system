package com.kamilrafalko.parkingsystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class StartResponse {

    private final String licenseNumber;
    private final LocalDateTime startDate;

    @JsonCreator
    public StartResponse(@JsonProperty("licenseNumber") String licenseNumber,
                         @JsonProperty("startDate") LocalDateTime startDate) {
        this.licenseNumber = licenseNumber;
        this.startDate = startDate;
    }
}
