package io.codelex.flightplanner.service;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AirportService {

    FlightRepository flightRepository;

    public AirportService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Airport> searchAirport(String input) {
        return flightRepository.getFlightList()
                               .stream()
                               .map(f -> Arrays.asList(f.getFrom(), f.getTo()))
                               .flatMap(List::stream)
                               .filter(a -> hasInput(a, input))
                               .toList();


    }

    public boolean hasInput(Airport airport, String input) {
        return hasSimilarStrings(airport.getAirport(), input) ||
                hasSimilarStrings(airport.getCity(), input) ||
                hasSimilarStrings(airport.getCountry(), input);
    }

    public boolean hasSimilarStrings(String string, String input) {
        return string.toLowerCase().contains(input.trim().toLowerCase());
    }
}
