CREATE SEQUENCE pedidos_id_seq
    START WITH 1
    INCREMENT BY 1;


CREATE TABLE pedidos (
    id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('pedidos_id_seq'),
    cliente_id INTEGER NOT NULL,
    laboratorio_id INTEGER NOT NULL,
    custo DECIMAL(10,2) NOT NULL,
    armacao VARCHAR(80),
    data_entrega TIMESTAMP,
    img_armacao TEXT,
    od DECIMAL(5,2),
    oe DECIMAL(5,2),
    ad DECIMAL(5,2),
    dnp DECIMAL(5,2),
    tratamento VARCHAR(120),
    tipo_lente VARCHAR(50),
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (cliente_id) REFERENCES clientes(id),

    CONSTRAINT fk_pedido_laboratorio
        FOREIGN KEY (laboratorio_id) REFERENCES laboratorios(id)
);
