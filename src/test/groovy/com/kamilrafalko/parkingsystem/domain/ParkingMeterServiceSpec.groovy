package com.kamilrafalko.parkingsystem.domain

import com.kamilrafalko.parkingsystem.domain.dto.StartRequest
import com.kamilrafalko.parkingsystem.domain.dto.StopRequest
import com.kamilrafalko.parkingsystem.domain.exceptions.IllegalParkingMeterOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
class ParkingMeterServiceSpec extends Specification {

    static isOnParkingLicense = 'AAA 123'
    static wasOnParkingLicense = 'CCC 444'
    static someVIPCode = 'ABC123'

    @Autowired
    ParkingMeterService parkingMeter

    @Autowired
    ParkingEntryRepository parkingEntryRepository

    @Autowired
    PaymentDueRepository paymentDueRepository

    def setup() {
        def notClosedEntry = new ParkingEntry(isOnParkingLicense, LocalDateTime.now(), someVIPCode)
        def closedEntry = new ParkingEntry(wasOnParkingLicense, LocalDateTime.now(), someVIPCode)
        closedEntry.endDate = LocalDateTime.now()

        parkingEntryRepository.save([notClosedEntry, closedEntry])
    }

    def cleanup() {
        parkingEntryRepository.deleteAll()
        paymentDueRepository.deleteAll()
    }

    def 'start should save entry when given car has never been on parking'() {
        given:
        def licenseNumber = 'A1B2C3'
        def car = new StartRequest(licenseNumber, null)

        when:
        def response = parkingMeter.start(car)

        then:
        parkingEntryRepository.findByLicenseNumberAndEndDateIsNull(licenseNumber)
            .map { entry -> entry.licenseNumber }
            .orElseGet { assert false } == licenseNumber
        response.licenseNumber == licenseNumber
    }

    def 'start should save entry when given car was parking'() {
        given:
        def licenseNumber = wasOnParkingLicense
        def car = new StartRequest(licenseNumber, null)

        when:
        def response = parkingMeter.start(car)

        then:
        parkingEntryRepository.findByLicenseNumberAndEndDateIsNull(licenseNumber)
                .map { entry -> entry.licenseNumber }
                .orElseGet { assert false } == licenseNumber
        response.licenseNumber == licenseNumber
    }

    def 'start should throw IllegalParkingMeterOperation when car with given license number is on parking'() {
        given:
        def car = new StartRequest(isOnParkingLicense, null)
        when:
        parkingMeter.start(car)

        then:
        thrown(IllegalParkingMeterOperation)
    }

    def 'stop should update entry end date and payment due when given car is on parking'() {
        given:
        def stopRequest = new StopRequest(isOnParkingLicense)
        def parkingEntry = parkingEntryRepository.findByLicenseNumberAndEndDateIsNull(isOnParkingLicense)
        def entryId = parkingEntry.orElseGet { assert false }.id

        when:
        def response = parkingMeter.stop(stopRequest)


        def updatedEntry = parkingEntryRepository.findOne(entryId)
        then:
        updatedEntry.endDate != null
        updatedEntry.paymentDue != null
        response.licenseNumber == isOnParkingLicense
    }

    def 'stop should throw IllegalParkingMeterOperation when given car is not on parking'() {
        given:
        def stopRequest = new StopRequest(wasOnParkingLicense)

        when:
        parkingMeter.stop(stopRequest)

        then:
        thrown(IllegalParkingMeterOperation)
    }

}
