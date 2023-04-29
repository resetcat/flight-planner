package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightDBRepository extends JpaRepository<Flight, Integer> {
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
            "FROM Flight f WHERE f.from = :from AND f.to = :to AND f.carrier = :carrier " +
            "AND f.departureTime = :departureTime AND f.arrivalTime = :arrivalTime")
    boolean checkIfAlreadyInList(@Param("from") Airport from, @Param("to") Airport to,
                                 @Param("carrier") String carrier,
                                 @Param("departureTime") LocalDateTime departureTime,
                                 @Param("arrivalTime") LocalDateTime arrivalTime);


    @Query("SELECT f FROM Flight f WHERE f.from = :from AND f.to = :to AND f.departureTime = :departureTime")
    List<Flight> findRequested(@Param("from") Airport from,
                               @Param("to") Airport to,
                               @Param("departureTime") LocalDateTime departureTime);


}
