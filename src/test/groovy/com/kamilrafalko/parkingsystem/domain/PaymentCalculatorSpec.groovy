package com.kamilrafalko.parkingsystem.domain

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class PaymentCalculatorSpec extends Specification {

    static rootDate = LocalDateTime.of(2017, 11, 20, 15, 30, 15)

    @Shared
    def regularConfig = new PricingConfig(1, 2, 2)
    @Shared
    def vipConfig = new PricingConfig(0, 2, 1.5)

    @Unroll
    def 'calculate should return #expectedResult for #hours hours on parking with config: #config.toString()'() {

        given:
        def calculator = new PaymentCalculator(config)

        when:
        def result = calculator.calculate(startDate, endDate)

        then:
        result == expectedResult

        where:
        startDate | endDate                              | config        || expectedResult
        rootDate  | rootDate.plusSeconds(1)              | vipConfig     || 0
        rootDate  | rootDate.plusHours(1)                | vipConfig     || 0
        rootDate  | rootDate.plusHours(1).plusMinutes(1) | vipConfig     || 2
        rootDate  | rootDate.plusHours(2).plusMinutes(1) | vipConfig     || 5
        rootDate  | rootDate.plusHours(3).plusMinutes(1) | vipConfig     || 9.5
        rootDate  | rootDate.plusSeconds(1)              | regularConfig || 1
        rootDate  | rootDate.plusHours(1).plusMinutes(1) | regularConfig || 3
        rootDate  | rootDate.plusHours(2).plusMinutes(1) | regularConfig || 7
        rootDate  | rootDate.plusHours(3).plusMinutes(1) | regularConfig || 15
    }

    def 'should throw IllegalArgumentException when endDate is before start date'() {
        given:
        def calculator = new PaymentCalculator(vipConfig)

        when:
        calculator.calculate(rootDate, rootDate.minusHours(1))

        then:
        thrown(IllegalArgumentException)
    }
}
