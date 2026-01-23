CREATE SEQUENCE laboratorios_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE laboratorios (
    id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('laboratorios_id_seq'),
    nome VARCHAR(120) NOT NULL,
    endereco VARCHAR (150) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
