package com.kamilrafalko.parkingsystem.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Component
class PricingConfigResolver {

    private final PricingConfig regularPricing;
    private final PricingConfig vipPricing;

    private final String vipCode;

    PricingConfigResolver(@Value("${parking-price.regular.first}") String regularFirst,
                          @Value("${parking-price.regular.second}") String regularSecond,
                          @Value("${parking-price.regular.third-and-each-next-multiplier}")
                                  String regularNextMultiplier,
                          @Value("${parking-price.vip.first}") String vipFirst,
                          @Value("${parking-price.vip.second}") String vipSecond,
                          @Value("${parking-price.vip.third-and-each-next-multiplier}")
                                  String vipNextMultiplier,
                          @Value("${vip-code}") String vipCode) {

        this.regularPricing = new PricingConfig(new BigDecimal(regularFirst),
                new BigDecimal(regularSecond), new BigDecimal(regularNextMultiplier));
        this.vipPricing = new PricingConfig(new BigDecimal(vipFirst),
                new BigDecimal(vipSecond), new BigDecimal(vipNextMultiplier));

        this.vipCode = vipCode;
    }

    PricingConfig resolveConfig(String vipCode) {
        return !StringUtils.isEmpty(vipCode) && vipCode.equals(this.vipCode) ? vipPricing : regularPricing;
    }

}
