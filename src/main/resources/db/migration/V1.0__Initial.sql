create table aircraft.aircraft (
        aircraft_uuid uuid not null,
        base varchar(255),
        registration varchar(255),
        taxes_paid boolean,
        type varchar(255),
        user_uuid uuid not null,
        primary key (aircraft_uuid)
    );