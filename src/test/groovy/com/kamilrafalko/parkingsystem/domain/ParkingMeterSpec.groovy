package com.kamilrafalko.parkingsystem.domain

import com.kamilrafalko.parkingsystem.domain.dto.Car
import com.kamilrafalko.parkingsystem.domain.exceptions.IllegalParkingMeterOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
class ParkingMeterSpec extends Specification {

    static isOnParkingLicense = 'AAA 123'
    static wasOnParkingLicense = 'CCC 444'

    @Autowired
    ParkingMeter parkingMeter

    @Autowired
    ParkingEntryRepository repository

    def setup() {
        def notClosedEntry = new ParkingEntry(isOnParkingLicense, LocalDateTime.now())
        def closedEntry = new ParkingEntry(wasOnParkingLicense, LocalDateTime.now())
        closedEntry.endDate = LocalDateTime.now()

        repository.save([notClosedEntry, closedEntry])
    }

    def cleanup() {
        repository.deleteAll()
    }

    def 'start should save entry when given car has never been on parking'() {
        given:
        def licenseNumber = 'A1B2C3'
        def car = new Car(licenseNumber)

        when:
        def response = parkingMeter.start(car)

        then:
        repository.findByLicenseNumberAndEndDateIsNull(licenseNumber)
            .map { entry -> entry.licenseNumber }
            .orElseGet { assert false } == licenseNumber
        response.licenseNumber == licenseNumber
    }

    def 'start should save entry when given car was parking'() {
        given:
        def licenseNumber = wasOnParkingLicense
        def car = new Car(licenseNumber)

        when:
        def response = parkingMeter.start(car)

        then:
        repository.findByLicenseNumberAndEndDateIsNull(licenseNumber)
                .map { entry -> entry.licenseNumber }
                .orElseGet { assert false } == licenseNumber
        response.licenseNumber == licenseNumber
    }

    def 'start should throw IllegalParkingMeterOperation when car with given license number is on parking'() {
        given:
        def car = new Car(isOnParkingLicense)
        when:
        parkingMeter.start(car)

        then:
        thrown(IllegalParkingMeterOperation)
    }

    def 'stop should update entry end date when given car is on parking'() {
        given:
        def car = new Car(isOnParkingLicense)

        when:
        def response = parkingMeter.stop(car)

        then:
        repository.findByLicenseNumberAndEndDateIsNull(isOnParkingLicense) == Optional.empty()
        response.licenseNumber == isOnParkingLicense
    }

    def 'stop should throw IllegalParkingMeterOperation when given car is not on parking'() {
        given:
        def car = new Car(wasOnParkingLicense)

        when:
        parkingMeter.stop(car)

        then:
        thrown(IllegalParkingMeterOperation)
    }

}
