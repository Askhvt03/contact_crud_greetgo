--liquibase formatted sql

CREATE TABLE IF NOT EXISTS t_contact
(
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(255)       NOT NULL,
    birth_date          DATE               NOT NULL,
    phone_number        VARCHAR(32) UNIQUE NOT NULL,
    second_phone_number VARCHAR(32)        NOT NULL,
    created_at          TIMESTAMP          NOT NULL

);