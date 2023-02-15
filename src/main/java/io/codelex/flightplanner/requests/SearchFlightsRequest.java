package io.codelex.flightplanner.requests;

import io.codelex.flightplanner.models.Airport;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class SearchFlightsRequest {
    @NotNull
    private Airport from;
    @NotNull
    private Airport to;

    @NotNull
    private LocalDate departureDate;

    public SearchFlightsRequest(Airport from, Airport to, LocalDate departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }
}
