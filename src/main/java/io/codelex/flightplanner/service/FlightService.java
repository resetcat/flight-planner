package io.codelex.flightplanner.service;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.requests.AddFlightRequest;
import io.codelex.flightplanner.requests.SearchFlightsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public interface FlightService {


    Flight addFlight(AddFlightRequest request);


    default void checkRequest(Flight flight) {
        if (checkIfAlreadyInList(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else if (flight.getFrom().equals(flight.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (!flight.getDepartureTime().isBefore(flight.getArrivalTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    };

    boolean checkIfAlreadyInList(Flight flight);

    Flight getFlightByID(int id);

    void deleteFlight(int id);

    default List<Flight> searchFlights(SearchFlightsRequest request) {
        if (request.getFrom().equals(request.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return findRequested(request);
    };


    List<Flight> findRequested(SearchFlightsRequest request);

    void clearFlights();

    List<Airport> searchAirport(String input);
}
