CREATE TABLE persons (
    id SERIAL,
    name TEXT PRIMARY KEY,
    licence BOOLEAN,
    car_number INTEGER REFERENCES cars (car_number)
);

CREATE TABLE cars (
    id SERIAL,
    mark TEXT,
    model TEXT,
    cost NUMERIC,
    car_number INTEGER PRIMARY KEY
);