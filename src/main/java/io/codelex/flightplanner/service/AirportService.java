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

    public List<Airport> searchText(String input) {
        return flightRepository.getFlightList()
                               .stream()
                               .map(f -> Arrays.asList(f.getFrom(), f.getTo()))
                               .flatMap(List::stream)
                               .filter(a -> a.containsText(input))
                               .toList();


    }
}
