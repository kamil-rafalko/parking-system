package com.kamilrafalko.parkingsystem.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
class ParkingFinancesServiceSpec extends Specification {

    static thirdOfMarch = LocalDate.of(2017, 3, 3)
    static secondOfMarch = LocalDate.of(2017, 3, 2)
    static firstOfMarch = LocalDate.of(2017, 3, 1)

    @Autowired
    PaymentDueRepository repository

    @Autowired
    ParkingFinancesService service

    def setup() {
        repository.save(new PaymentDue(2, thirdOfMarch))
        repository.save(new PaymentDue(5, thirdOfMarch))
        repository.save(new PaymentDue(1, secondOfMarch))
    }

    def cleanup() {
        repository.deleteAll()
    }

    def 'should return parking income for given day'() {
        when:
        def incomeDto = service.countIncomeFor(date)

        then:
        incomeDto.income == expectedIncome

        where:
        date          || expectedIncome
        thirdOfMarch  || 7
        secondOfMarch || 1
        firstOfMarch  || 0
    }

}
