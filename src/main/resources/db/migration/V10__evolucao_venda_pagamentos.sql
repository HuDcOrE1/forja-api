CREATE TABLE pagamento_venda
(
    id BIGSERIAL PRIMARY KEY,

    venda_id BIGINT NOT NULL,

    forma_pagamento VARCHAR(50) NOT NULL,

    valor NUMERIC(14,2) NOT NULL,

    CONSTRAINT fk_pagamento_venda
        FOREIGN KEY (venda_id)
        REFERENCES venda(id)
);