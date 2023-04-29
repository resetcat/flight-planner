package io.codelex.flightplanner.service;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.repository.FlightRepository;
import io.codelex.flightplanner.requests.AddFlightRequest;
import io.codelex.flightplanner.requests.SearchFlightsRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = "flight-planner", name = "storage-type", havingValue = "in-memory")
public class FlightInMemoryService implements FlightService {
    private final FlightRepository flightRepository;
    private int idCounter = 0;

    public FlightInMemoryService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(AddFlightRequest request) {
        Flight newFlight = new Flight(request);
        checkRequest(newFlight);
        newFlight.setId(++idCounter);
        flightRepository.addFlight(newFlight);
        return newFlight;
    }

    public boolean checkIfAlreadyInList(Flight flight) {
        return flightRepository.getFlightList()
                               .stream()
                               .anyMatch(f -> f.getFrom().equals(flight.getFrom()) &&
                                       f.getTo().equals(flight.getTo()) &&
                                       f.getCarrier().equals(flight.getCarrier()) &&
                                       f.getDepartureTime().equals(flight.getDepartureTime()) &&
                                       f.getArrivalTime().equals(flight.getArrivalTime()));
    }

    public Flight getFlightByID(int id) {
        return flightRepository.getFlightList()
                               .stream()
                               .filter(f -> f.getId() == id)
                               .findAny()
                               .orElseThrow(
                                       () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteFlight(int id) {
        flightRepository.getFlightList().removeIf(f -> f.getId() == id);
    }



    public List<Flight> findRequested(SearchFlightsRequest request) {
        return flightRepository.getFlightList()
                               .stream()
                               .filter(flight -> flight.getFrom()
                                                       .getAirport()
                                                       .equals(request.getFrom()) &&
                                       flight.getTo().getAirport().equals(request.getTo()) &&
                                       flight.getDepartureTime()
                                             .toLocalDate()
                                             .equals(request.getDepartureDate()))
                               .toList();
    }

    @Override
    public void clearFlights() {
        flightRepository.clearFlights();
    }

    public List<Airport> searchAirport(String input) {
        return flightRepository.getFlightList()
                               .stream()
                               .map(f -> Arrays.asList(f.getFrom(), f.getTo()))
                               .flatMap(List::stream)
                               .filter(a -> a.containsText(input))
                               .toList();
    }
}
