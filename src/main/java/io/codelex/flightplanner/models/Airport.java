package io.codelex.flightplanner.models;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Airport {
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public boolean containsText(String input) {
        return containsIgnoringCase(getAirport(), input) ||
                containsIgnoringCase(getCity(), input) ||
                containsIgnoringCase(getCountry(), input);
    }

    public boolean containsIgnoringCase(String text, String searchText) {
        return text.toLowerCase().contains(searchText.trim().toLowerCase());
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Airport airport1 = (Airport) o;
        return country.trim().equalsIgnoreCase(airport1.country.trim()) &&
                city.trim().equalsIgnoreCase(airport1.city.trim()) &&
                airport.trim().equalsIgnoreCase(airport1.airport.trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }

    @Override
    public String toString() {
        return "Airport{" + "country='" + country + '\'' + ", city='" + city + '\'' +
                ", airport='" + airport + '\'' + '}';
    }
}
