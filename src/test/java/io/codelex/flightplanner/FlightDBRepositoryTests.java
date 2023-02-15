package io.codelex.flightplanner;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.repository.FlightDBRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class FlightDBRepositoryTests {

    @Autowired
    private FlightDBRepository flightRepository;

    @Test
    public void testCheckIfAlreadyInList() {
        Airport from = new Airport("USA", "New York", "JFK");
        Airport to = new Airport("UK", "London", "LHR");

        Flight flight1 = new Flight(from, to, "carrier", LocalDateTime.of(2023, 3, 1, 10, 0),
                                    LocalDateTime.of(2023, 3, 1, 12, 0));
        flightRepository.save(flight1);

        boolean result1 = flightRepository.checkIfAlreadyInList(from, to, "carrier",
                                                                LocalDateTime.of(2023, 3, 1, 10, 0),
                                                                LocalDateTime.of(2023, 3, 1, 12,
                                                                                 0));
        assertTrue(result1);

        boolean result2 = flightRepository.checkIfAlreadyInList(to, from, "carrier",
                                                                LocalDateTime.of(2023, 3, 1, 10, 0),
                                                                LocalDateTime.of(2023, 3, 1, 12,
                                                                                 0));
        assertFalse(result2);

        boolean result3 = flightRepository.checkIfAlreadyInList(from, to, "otherCarrier",
                                                                LocalDateTime.of(2023, 3, 1, 10, 0),
                                                                LocalDateTime.of(2023, 3, 1, 12,
                                                                                 0));
        assertFalse(result3);

        boolean result4 = flightRepository.checkIfAlreadyInList(from, to, "carrier",
                                                                LocalDateTime.of(2023, 3, 1, 11, 0),
                                                                LocalDateTime.of(2023, 3, 1, 13,
                                                                                 0));
        assertFalse(result4);
    }

    @Test
    void testFindRequested() {
        Airport from = new Airport("USA", "New York", "JFK");
        Airport to = new Airport("UK", "London", "LHR");
        String carrier = "American Airlines";
        LocalDateTime departureTime = LocalDateTime.of(2023, 3, 1, 10, 30);
        LocalDateTime arrivalTime = LocalDateTime.of(2023, 3, 1, 14, 30);
        Flight flight1 = new Flight(from, to, carrier, departureTime, arrivalTime);
        Flight flight2 = new Flight(from, to, carrier, departureTime, arrivalTime);
        Flight flight3 = new Flight(to, from, carrier, departureTime, arrivalTime);

        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);

        List<Flight> results = flightRepository.findRequested(from, to, departureTime);

        assertEquals(2, results.size());
        assertTrue(results.contains(flight1));
        assertTrue(results.contains(flight2));
        assertFalse(results.contains(flight3));
    }

}
