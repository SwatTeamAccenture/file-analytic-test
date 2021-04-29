DROP TABLE csvmapper IF EXISTS;

CREATE TABLE csvmapper  (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    identificador VARCHAR(50),
    coluna1 VARCHAR(250),
    coluna2 VARCHAR(250),
    coluna3 VARCHAR(250)
);