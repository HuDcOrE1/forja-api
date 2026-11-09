CREATE TABLE produto
(
    id BIGSERIAL PRIMARY KEY,

    nome VARCHAR(150) NOT NULL,

    descricao VARCHAR(500),

    sku VARCHAR(100) NOT NULL UNIQUE,

    quantidade_estoque INTEGER NOT NULL,

    valor_unitario NUMERIC(12,2) NOT NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_produto_nome
    ON produto(nome);

CREATE INDEX idx_produto_sku
    ON produto(sku);

INSERT INTO produto
(
    nome,
    descricao,
    sku,
    quantidade_estoque,
    valor_unitario,
    ativo
)
VALUES
(
    'Munição .380',
    'Caixa com 50 unidades',
    'MUN-380-001',
    100,
    185.90,
    true
);

INSERT INTO produto
(
    nome,
    descricao,
    sku,
    quantidade_estoque,
    valor_unitario,
    ativo
)
VALUES
(
    'Óculos de Proteção',
    'Óculos para prática esportiva',
    'EPI-001',
    20,
    49.90,
    true
);

INSERT INTO produto
(
    nome,
    descricao,
    sku,
    quantidade_estoque,
    valor_unitario,
    ativo
)
VALUES
(
    'Protetor Auricular',
    'Proteção auditiva',
    'EPI-002',
    50,
    29.90,
    true
);