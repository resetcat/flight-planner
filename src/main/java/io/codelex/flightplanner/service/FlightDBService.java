package io.codelex.flightplanner.service;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.repository.AirportDBRepository;
import io.codelex.flightplanner.repository.FlightDBRepository;
import io.codelex.flightplanner.requests.AddFlightRequest;
import io.codelex.flightplanner.requests.SearchFlightsRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "flight-planner", name = "storage-type", havingValue = "database")
public class FlightDBService implements FlightService {
    private final FlightDBRepository flightRepository;
    private final AirportDBRepository airportRepository;



    public FlightDBService(FlightDBRepository flightRepository,
                           AirportDBRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    synchronized public Flight addFlight(AddFlightRequest request) {
        Flight newFlight = new Flight(request);
        newFlight.setFrom(addIfAirportIfExists(newFlight.getFrom()));
        newFlight.setTo(addIfAirportIfExists(newFlight.getTo()));
        checkRequest(newFlight);
        return flightRepository.save(newFlight);
    }
    synchronized private Airport addIfAirportIfExists(Airport airport) {
        Optional<Airport> existingAirport = airportRepository.findById(airport.getAirport());
        return existingAirport.orElseGet(() -> airportRepository.save(airport));
    }



    public boolean checkIfAlreadyInList(Flight flight) {
        return flightRepository.findAll()
                               .stream()
                               .anyMatch(f -> f.getFrom().equals(flight.getFrom()) &&
                                       f.getTo().equals(flight.getTo()) &&
                                       f.getCarrier().equals(flight.getCarrier()) &&
                                       f.getDepartureTime().equals(flight.getDepartureTime()) &&
                                       f.getArrivalTime().equals(flight.getArrivalTime()));
    }

    public Flight getFlightByID(int id) {
        return flightRepository.findById(id)
                               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteFlight(int id) {
        flightRepository.findById(id).ifPresent(flightRepository::delete);
    }



    public List<Flight> findRequested(SearchFlightsRequest request) {
        return flightRepository.findAll()
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
        flightRepository.deleteAll();
    }

    public List<Airport> searchAirport(String input) {
        return flightRepository.findAll()
                               .stream()
                               .map(f -> Arrays.asList(f.getFrom(), f.getTo()))
                               .flatMap(List::stream)
                               .filter(a -> a.containsText(input))
                               .toList();
    }
}
