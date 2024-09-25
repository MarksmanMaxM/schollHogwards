CREATE TABLE students (
    id SERIAL,
    age INTEGER CHECK ( age > 15 ) DEFAULT 20,
    name TEXT PRIMARY KEY,
    faculty_id SERIAL REFERENCES faculties (id)
);

CREATE TABLE faculties (
    id SERIAL,
    name TEXT PRIMARY KEY,
    color TEXT
);

ALTER TABLE faculties
    ADD CONSTRAINT fac_col_unique UNIQUE (name, color);


