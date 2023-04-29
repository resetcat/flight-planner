package io.codelex.flightplanner.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.requests.AddFlightRequest;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "airport_from")
    private Airport from;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "airport_to")
    private Airport to;
    private String carrier;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;

    public Flight(@NonNull Airport from, @NonNull Airport to, @NonNull String carrier,
                  LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight(AddFlightRequest request) {
        this.from = request.getFrom();
        this.to = request.getTo();
        this.carrier = request.getCarrier();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.departureTime = LocalDateTime.parse(request.getDepartureTime(), formatter);
        this.arrivalTime = LocalDateTime.parse(request.getArrivalTime(), formatter);
    }

    public Flight() {

    }

    public Flight(Airport from, Airport to, LocalDateTime departureTime) {
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(@NonNull Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(@NonNull Airport to) {
        this.to = to;
    }

    @NonNull
    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(@NonNull String carrier) {
        this.carrier = carrier;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Flight flight = (Flight) o;
        return id == flight.id && from.equals(flight.from) && to.equals(flight.to) &&
                carrier.equals(flight.carrier) && departureTime.equals(flight.departureTime) &&
                arrivalTime.equals(flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, carrier, departureTime, arrivalTime);
    }
}
