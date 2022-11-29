package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.requests.PageResult;
import io.codelex.flightplanner.requests.SearchFlightsRequest;
import io.codelex.flightplanner.service.AirportService;
import io.codelex.flightplanner.service.FlightService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final FlightService flightService;
    private final AirportService airportService;

    public CustomerController(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }


    @GetMapping("/flights/{id}")
    public Flight getFlightById(@PathVariable("id") int id) {
        return flightService.getFlightByID(id);
    }

    @GetMapping("/airports")
    public List<Airport> searchAirport(@RequestParam String search) {
        return airportService.searchText(search);
    }

    @PostMapping("/flights/search")
    public PageResult searchFlights(
            @Valid @NotNull @RequestBody SearchFlightsRequest request) {
        List<Flight> foundResult = flightService.searchFlights(request);
        return new PageResult(0, foundResult.size(), foundResult);
    }

}
