CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL,
    laboratorio_id INTEGER NOT NULL,

    custo DECIMAL(10,2) NOT NULL,
    armacao TEXT,
    data_entrega TIMESTAMP,
    data_emissao TIMESTAMP,

    img_armacao TEXT,

    od DECIMAL(5,2),
    oe DECIMAL(5,2),
    ad DECIMAL(5,2),
    dnp DECIMAL(5,2),

    tratamento TEXT,
    tipo_lente TEXT,
    status VARCHAR(30) NOT NULL,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (cliente_id) REFERENCES clientes(id),

    CONSTRAINT fk_pedido_laboratorio
        FOREIGN KEY (laboratorio_id) REFERENCES laboratorios(id)
);
