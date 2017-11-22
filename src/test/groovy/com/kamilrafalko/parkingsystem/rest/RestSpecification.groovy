package com.kamilrafalko.parkingsystem.rest

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Shared
import spock.lang.Specification

class RestSpecification extends Specification {

    @Shared
    protected ObjectMapper objectMapper

    def setupSpec() {
        objectMapper  = new ObjectMapper()
        objectMapper.findAndRegisterModules()
    }
}
