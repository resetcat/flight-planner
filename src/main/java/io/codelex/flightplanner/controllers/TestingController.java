package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.repository.FlightRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestingController {
    private final FlightRepository flightRepository;

    public TestingController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @PostMapping("/clear")
    public void clearFlights() {
        flightRepository.clearFlights();
    }
}
