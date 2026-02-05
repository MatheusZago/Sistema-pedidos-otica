CREATE SEQUENCE lentes_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE lentes (
    id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('lentes_id_seq'),
    tipo_lente VARCHAR(120) NOT NULL,
    custo DECIMAL(10,2) NOT NULL,
    tratamento VARCHAR(120) NOT NULL,
    indice VARCHAR(120) NOT NULL,
    valor_venda DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);



