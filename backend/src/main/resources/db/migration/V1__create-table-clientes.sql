CREATE SEQUENCE clientes_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE clientes (
    id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('clientes_id_seq'),
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    email VARCHAR(80) UNIQUE,
    foto VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
)