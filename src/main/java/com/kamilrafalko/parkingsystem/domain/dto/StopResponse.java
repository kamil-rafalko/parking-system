package com.kamilrafalko.parkingsystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class StopResponse {

    private final String licenseNumber;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    @JsonCreator
    public StopResponse(@JsonProperty("licenseNumber") String licenseNumber,
                        @JsonProperty("startDate") LocalDateTime startDate,
                        @JsonProperty("endDate") LocalDateTime endDate) {
        this.licenseNumber = licenseNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
