package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.models.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository {

   final private List<Flight> flightList = new ArrayList<>();


   public List<Flight> getFlightList() {
      return flightList;
   }


   public void addFlight(Flight name){
      flightList.add(name);
   }

   public void clearFlights(){
      flightList.clear();
   }
}
