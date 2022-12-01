package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// TODO @ConditionalOnProperty
public interface AirportDBRepository extends JpaRepository<Airport, String> {
}
