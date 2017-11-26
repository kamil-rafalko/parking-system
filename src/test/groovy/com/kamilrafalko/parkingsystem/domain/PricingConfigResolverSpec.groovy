package com.kamilrafalko.parkingsystem.domain

import spock.lang.Shared
import spock.lang.Specification

class PricingConfigResolverSpec extends Specification {

    static regularFirst = '1'
    static regularSecond = '2'
    static regularNextMultiplier = '2'
    static vipFirst = '0'
    static vipSecond = '2'
    static vipNextMultiplier = '1.5'
    static vipCode = 'VIP_CODE'

    @Shared
    def vipConfig = new PricingConfig(new BigDecimal(vipFirst), new BigDecimal(vipSecond),
            new BigDecimal(vipNextMultiplier))
    @Shared
    def regularConfig = new PricingConfig(new BigDecimal(regularFirst), new BigDecimal(regularSecond),
            new BigDecimal(regularNextMultiplier))

    def 'should correctly resolve config according to given vipCode'() {
        given:
        def resolver = new PricingConfigResolver(regularFirst, regularSecond, regularNextMultiplier, vipFirst, 
                vipSecond, vipNextMultiplier, vipCode)

        when:
        def config = resolver.resolveConfig(givenCode)
        
        then:
        config == expectedConfig

        where:
        givenCode    || expectedConfig
        'WRONG_CODE' || regularConfig
        'VIP_CODE'   || vipConfig
    }

}
