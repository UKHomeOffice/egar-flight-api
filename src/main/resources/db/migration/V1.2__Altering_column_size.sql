UPDATE aircraft.aircraft SET base = LEFT(base, 200);
ALTER TABLE aircraft.aircraft ALTER COLUMN base type VARCHAR(200);

UPDATE aircraft.aircraft SET registration = LEFT(registration, 15);
ALTER TABLE aircraft.aircraft ALTER COLUMN registration type VARCHAR(15);

UPDATE aircraft.aircraft SET type = LEFT(type, 35);
ALTER TABLE aircraft.aircraft ALTER COLUMN "type" type VARCHAR(35);
