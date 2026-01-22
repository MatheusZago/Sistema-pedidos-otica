CREATE TABLE laboratorios (
    id SERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    endereco TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
