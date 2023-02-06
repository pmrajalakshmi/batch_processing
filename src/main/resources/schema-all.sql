DROP TABLE people IF EXISTS;

CREATE TABLE people  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    id VARCHAR(20),
    name VARCHAR(20),
    hours INT,
    pay DECIMAL

);