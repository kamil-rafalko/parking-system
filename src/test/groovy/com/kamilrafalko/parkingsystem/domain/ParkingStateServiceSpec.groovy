package com.kamilrafalko.parkingsystem.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
class ParkingStateServiceSpec extends Specification {

    static isOnParkingLicense = '123AB'
    static isNotOnParkingLicense = '123ABC'

    @Autowired
    ParkingStateService parkingStateService

    @Autowired
    ParkingEntryRepository repository


    def 'should return true only if car with given license number is on parking'() {
        given:
        def notClosedEntry = new ParkingEntry(isOnParkingLicense, LocalDateTime.now(), 'VIP_CODE')
        repository.save(notClosedEntry)

        when:
        def result = parkingStateService.getCarState(licenseNumber)

        then:
        result.onParking == expectedResult

        where:
        licenseNumber         || expectedResult
        isOnParkingLicense    || true
        isNotOnParkingLicense || false
    }
}
