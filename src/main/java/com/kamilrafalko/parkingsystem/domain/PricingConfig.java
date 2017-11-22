package com.kamilrafalko.parkingsystem.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode
class PricingConfig {

    private final BigDecimal firstHour;
    private final BigDecimal secondHour;
    private final BigDecimal thirdAndNextMultiplier;

    PricingConfig(BigDecimal firstHour, BigDecimal secondHour, BigDecimal thirdAndNextMultiplier) {
        this.firstHour = firstHour;
        this.secondHour = secondHour;
        this.thirdAndNextMultiplier = thirdAndNextMultiplier;
    }
}
