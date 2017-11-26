package com.kamilrafalko.parkingsystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class StartRequest {

    private final String licenseNumber;
    private final String vipCode;

    @JsonCreator
    public StartRequest(@JsonProperty("licenseNumber") String licenseNumber,
                        @JsonProperty("vipCode") String vipCode) {
        this.licenseNumber = licenseNumber;
        this.vipCode = vipCode;
    }
}
