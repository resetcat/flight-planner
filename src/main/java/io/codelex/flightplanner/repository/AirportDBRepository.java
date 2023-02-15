package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportDBRepository extends JpaRepository<Airport, String> {
    @Query("SELECT a FROM Airport a " +
            "WHERE a.country LIKE %:input% " +
            "OR a.city LIKE %:input% " +
            "OR a.airport LIKE %:input%")
    List<Airport> searchAirport(@Param("input") String input);


}
