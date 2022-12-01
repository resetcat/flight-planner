--liquibase formatted sql

--changeset Mihails:1

CREATE TABLE airports
(
    airport VARCHAR(255) PRIMARY KEY,
    country VARCHAR(255) NOT NULL ,
    city VARCHAR(255) NOT NULL
)

