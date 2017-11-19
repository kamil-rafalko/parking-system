package com.kamilrafalko.parkingsystem.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.kamilrafalko.parkingsystem.domain.ParkingMeter
import com.kamilrafalko.parkingsystem.domain.dto.Car
import com.kamilrafalko.parkingsystem.domain.dto.StartResponse
import com.kamilrafalko.parkingsystem.domain.dto.StopResponse
import com.kamilrafalko.parkingsystem.domain.exceptions.IllegalParkingMeterOperation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class ParkingMeterControllerSpec extends Specification {

    def parkingMeter = Stub(ParkingMeter)
    def parkingMeterController = new ParkingMeterController(parkingMeter)
    def advice = new ExceptionHandlingAdvice()

    def mockMvc = standaloneSetup(parkingMeterController)
            .setControllerAdvice(advice)
            .build()

    def testCar = new Car('123AAA')

    @Shared
    ObjectMapper objectMapper

    def setupSpec() {
        objectMapper  = new ObjectMapper()
        objectMapper.findAndRegisterModules()
    }

    def 'start should return CREATED when parking meter starts correctly'() {

        given:
        def expectedResponse = new StartResponse(testCar.licenseNumber, LocalDateTime.now())
        parkingMeter.start(testCar) >> expectedResponse

        when:
        def response = mockMvc.perform(post('/meter/start')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(testCar))).andReturn().response

        then:
        response.status == HttpStatus.CREATED.value()
        response.getContentType() == MediaType.APPLICATION_JSON_UTF8_VALUE
        def responseJson = objectMapper.readValue(response.getContentAsString(), StartResponse.class)
        responseJson == expectedResponse
    }

    def 'start should return bad request if parking meter throws IllegalParkingMeterOperation'() {

        given:
        def errorMessage = 'Mocked exception'
        def exception = new IllegalParkingMeterOperation(errorMessage)
        def expectedResponse = new ErrorResponse(exception.getClass().getName(), errorMessage)
        parkingMeter.start(testCar) >> { throw exception }

        when:
        def response = mockMvc.perform(post('/meter/start')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(testCar))).andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        response.getContentType() == MediaType.APPLICATION_JSON_UTF8_VALUE
        def responseJson = objectMapper.readValue(response.getContentAsString(), ErrorResponse)
        responseJson == expectedResponse
    }

    def 'stop should return OK when parking meter stops correctly' () {
        given:
        def expectedResponse = new StopResponse(testCar.licenseNumber,
                LocalDateTime.now().minusHours(3), LocalDateTime.now())
        parkingMeter.stop(testCar) >> expectedResponse

        when:
        def response = mockMvc.perform(post('/meter/stop')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(testCar))).andReturn().response

        then:
        response.status == HttpStatus.OK.value()
        response.getContentType() == MediaType.APPLICATION_JSON_UTF8_VALUE
        def responseJson = objectMapper.readValue(response.getContentAsString(), StopResponse.class)
        responseJson == expectedResponse
    }

    def 'stop should return bad request if parking meter throws IllegalParkingMeterOperation'() {

        given:
        def errorMessage = 'Mocked exception'
        def exception = new IllegalParkingMeterOperation(errorMessage)
        def expectedResponse = new ErrorResponse(exception.getClass().getName(), errorMessage)
        parkingMeter.stop(testCar) >> { throw exception }

        when:
        def response = mockMvc.perform(post('/meter/stop')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(testCar))).andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        response.getContentType() == MediaType.APPLICATION_JSON_UTF8_VALUE
        def responseJson = objectMapper.readValue(response.getContentAsString(), ErrorResponse)
        responseJson == expectedResponse
    }


}
