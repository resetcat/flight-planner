package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.requests.AddFlightRequest;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminFlightController {
    FlightService flightService;

    public AdminFlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public synchronized Flight addFlight(@RequestBody @Valid AddFlightRequest flightRequest) {
       return flightService.addFlight(flightRequest);
    }

    @GetMapping("/flights/{id}")
    public Flight getFlightById(@PathVariable("id") int id){
        return flightService.getFlightByID(id);
    }

    @DeleteMapping ("/flights/{id}")
    public synchronized void deleteFlight(@PathVariable int id){
        flightService.deleteFlight(id);

    }
}
