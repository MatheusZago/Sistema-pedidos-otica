CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    telefone TEXT NOT NULL,
    email TEXT,
    foto TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);