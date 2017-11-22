package com.kamilrafalko.parkingsystem.rest;

import com.kamilrafalko.parkingsystem.domain.ParkingMeterService;
import com.kamilrafalko.parkingsystem.domain.dto.StartRequest;
import com.kamilrafalko.parkingsystem.domain.dto.StartResponse;
import com.kamilrafalko.parkingsystem.domain.dto.StopRequest;
import com.kamilrafalko.parkingsystem.domain.dto.StopResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingMeterController {

    private final ParkingMeterService parkingMeterService;

    @Autowired
    public ParkingMeterController(ParkingMeterService parkingMeterService) {
        this.parkingMeterService = parkingMeterService;
    }

    @PostMapping("meter/start")
    public ResponseEntity<StartResponse> start(@RequestBody StartRequest startRequest) {
        StartResponse response = parkingMeterService.start(startRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("meter/stop")
    public ResponseEntity<StopResponse> stop(@RequestBody StopRequest stopRequest) {
        StopResponse response =  parkingMeterService.stop(stopRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
