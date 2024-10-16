-- liquibase formatted sql

-- changeset mMax:1
CREATE INDEX student_id ON student (name);

-- changeset mMax:2
CREATE INDEX faculty_id ON faculty (name);
