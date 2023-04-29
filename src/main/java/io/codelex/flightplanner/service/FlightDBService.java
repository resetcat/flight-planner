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

import java.time.LocalDateTime;
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
        return flightRepository.checkIfAlreadyInList(flight.getFrom(), flight.getTo(),
                                                     flight.getCarrier(), flight.getDepartureTime(),
                                                     flight.getArrivalTime());
    }

    public Flight getFlightByID(int id) {
        return flightRepository.findById(id)
                               .orElseThrow(
                                       () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteFlight(int id) {
        flightRepository.findById(id).ifPresent(flightRepository::delete);
    }


    public List<Flight> findRequested(SearchFlightsRequest request) {
        return flightRepository.findRequested(request.getFrom(), request.getTo(),
                                              LocalDateTime.from(request.getDepartureDate()));
    }

    @Override
    public void clearFlights() {
        flightRepository.deleteAll();
    }

    public List<Airport> searchAirport(String input) {
       return airportRepository.searchAirport(input.toLowerCase());
//        return flightRepository.findAll()
//                               .stream()
//                               .map(f -> Arrays.asList(f.getFrom(), f.getTo()))
//                               .flatMap(List::stream)
//                               .filter(a -> a.containsText(input))
//                               .toList();
    }
}
