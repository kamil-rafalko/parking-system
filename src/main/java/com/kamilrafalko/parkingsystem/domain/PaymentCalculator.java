package com.kamilrafalko.parkingsystem.domain;

import com.kamilrafalko.parkingsystem.domain.exceptions.MaxHoursLimitExceededException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

class PaymentCalculator {

    private final PricingConfig pricingConfig;

    PaymentCalculator(PricingConfig pricingConfig) {
        this.pricingConfig = pricingConfig;
    }

    BigDecimal calculate(LocalDateTime startDate, LocalDateTime endDate) {

        final int hoursOnParking = calculateNumberOfHours(startDate, endDate);

        if (hoursOnParking < 0)
           throw new IllegalArgumentException("Can not calculate payment for negative number of hours");

        BigDecimal lastHourPrice = BigDecimal.ZERO;
        BigDecimal price = BigDecimal.ZERO;
        for (int i = 1; i <= hoursOnParking; i++) {
            lastHourPrice = calculateForOneHour(i, lastHourPrice);
            price = price.add(lastHourPrice);
        }

        return price;
    }

    private int calculateNumberOfHours(LocalDateTime startDate, LocalDateTime endDate) {
        final Duration parkingDuration = Duration.between(startDate, endDate);
        final long fullHours = parkingDuration.toHours();
        final boolean isNextHourStarted = parkingDuration.minusHours(fullHours).isZero();

        final long result = isNextHourStarted ? fullHours : fullHours + 1;

        if (result > Integer.MAX_VALUE) {
            throw new MaxHoursLimitExceededException("Can not calculate payment for number of hours greater than " +
                    Integer.MAX_VALUE);
        } else {
            return (int) result;
        }
    }

    private BigDecimal calculateForOneHour(int hour, BigDecimal lastHourPrice) {
        switch (hour) {
            case 1:
                return pricingConfig.getFirstHour();
            case 2:
                return pricingConfig.getSecondHour();
            default:
                return calculateForThirdOrEachNextHour(lastHourPrice);
        }
    }

    private BigDecimal calculateForThirdOrEachNextHour(BigDecimal lastHourPrice) {
        return lastHourPrice.multiply(pricingConfig.getThirdAndNextMultiplier());
    }
}
