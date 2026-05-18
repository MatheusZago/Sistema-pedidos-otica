CREATE SEQUENCE usuarios_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE usuarios (
    id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('usuarios_id_seq'),

    username VARCHAR(50) NOT NULL UNIQUE,

    email VARCHAR(100) NOT NULL UNIQUE,

    senha VARCHAR(255) NOT NULL,

    role VARCHAR(20) NOT NULL,

    created_at TIMESTAMP,

    updated_at TIMESTAMP
);