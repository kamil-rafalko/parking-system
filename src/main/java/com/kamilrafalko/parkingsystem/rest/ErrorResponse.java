package com.kamilrafalko.parkingsystem.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
class ErrorResponse {

    private final String exception;
    private final String message;

    @JsonCreator
    ErrorResponse(@JsonProperty("exception") String exception,
                  @JsonProperty("message") String message) {
        this.exception = exception;
        this.message = message;
    }
}
