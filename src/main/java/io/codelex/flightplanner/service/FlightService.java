package io.codelex.flightplanner.service;

import io.codelex.flightplanner.requests.AddFlightRequest;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.requests.SearchFlightsRequest;
import io.codelex.flightplanner.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FlightService {
    FlightRepository flightRepository;
    int idCounter = 0;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(AddFlightRequest request) {
        Flight newFlight = new Flight(request);
        checkRequest(newFlight);
        newFlight.setId(++idCounter);
        flightRepository.addFlight(newFlight);
        return newFlight;
    }

    public void checkRequest(Flight flight) {
        if (checkIfAlreadyInList(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else if (flight.getFrom().equals(flight.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (!flight.getDepartureTime().isBefore(flight.getArrivalTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
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

    public List<Flight> searchFlights(SearchFlightsRequest request) {
        if (request.getFrom().equals(request.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return findRequested(request);
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
}
