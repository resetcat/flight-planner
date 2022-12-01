--liquibase formatted sql

--changeset Mihails:2

CREATE TABLE flights
(
    id             SERIAL PRIMARY KEY,
    airport_from   VARCHAR(255),
    airport_to     VARCHAR(255),
    carrier        VARCHAR(255),
    departure_time TIMESTAMP,
    arrival_time   TIMESTAMP,
    CONSTRAINT flights_airport_from_fk FOREIGN KEY (airport_from) REFERENCES airports (airport),
    CONSTRAINT flights_airport_to_fk FOREIGN KEY (airport_to) REFERENCES airports (airport)
);

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;