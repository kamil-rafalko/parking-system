package com.kamilrafalko.parkingsystem.rest

import com.kamilrafalko.parkingsystem.domain.ParkingMeterService
import com.kamilrafalko.parkingsystem.domain.dto.StartRequest
import com.kamilrafalko.parkingsystem.domain.dto.StartResponse
import com.kamilrafalko.parkingsystem.domain.dto.StopRequest
import com.kamilrafalko.parkingsystem.domain.dto.StopResponse
import com.kamilrafalko.parkingsystem.domain.exceptions.IllegalParkingMeterOperation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class ParkingMeterControllerSpec extends RestSpecification {

    def parkingMeter = Stub(ParkingMeterService)
    def parkingMeterController = new ParkingMeterController(parkingMeter)
    def advice = new ExceptionHandlingAdvice()

    def mockMvc = standaloneSetup(parkingMeterController)
            .setControllerAdvice(advice)
            .build()

    def startRequest = new StartRequest('123AAA', 'DASD123')
    def stopRequest = new StopRequest('123AAA')

    def 'start should return CREATED when parking meter starts correctly'() {

        given:
        def expectedResponse = new StartResponse(startRequest.licenseNumber, LocalDateTime.now())
        parkingMeter.start(startRequest) >> expectedResponse

        when:
        def response = mockMvc.perform(post('/meter/start')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(startRequest))).andReturn().response

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
        parkingMeter.start(startRequest) >> { throw exception }

        when:
        def response = mockMvc.perform(post('/meter/start')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(startRequest))).andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        response.getContentType() == MediaType.APPLICATION_JSON_UTF8_VALUE
        def responseJson = objectMapper.readValue(response.getContentAsString(), ErrorResponse)
        responseJson == expectedResponse
    }

    def 'stop should return OK when parking meter stops correctly' () {
        given:
        def somePrice = 1
        def expectedResponse = new StopResponse(startRequest.licenseNumber, LocalDateTime.now().minusHours(3),
                LocalDateTime.now(), somePrice)
        parkingMeter.stop(stopRequest) >> expectedResponse

        when:
        def response = mockMvc.perform(post('/meter/stop')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(stopRequest))).andReturn().response

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
        parkingMeter.stop(stopRequest) >> { throw exception }

        when:
        def response = mockMvc.perform(post('/meter/stop')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(stopRequest))).andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        response.getContentType() == MediaType.APPLICATION_JSON_UTF8_VALUE
        def responseJson = objectMapper.readValue(response.getContentAsString(), ErrorResponse)
        responseJson == expectedResponse
    }

}
