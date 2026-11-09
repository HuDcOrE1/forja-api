-- =====================================================
-- FORJA
-- V1 - Estrutura Inicial
-- =====================================================

-- =====================================================
-- PERFIS
-- =====================================================

CREATE TABLE perfil (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- USUÁRIOS
-- =====================================================

CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    alterado_em TIMESTAMP
);

CREATE TABLE usuario_perfil (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,

    PRIMARY KEY (usuario_id, perfil_id),

    CONSTRAINT fk_usuario_perfil_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id),

    CONSTRAINT fk_usuario_perfil_perfil
        FOREIGN KEY (perfil_id)
        REFERENCES perfil(id)
);

-- =====================================================
-- CLIENTES
-- =====================================================

CREATE TABLE cliente (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(150),
    cr VARCHAR(50),
    validade_cr DATE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    alterado_em TIMESTAMP
);

-- =====================================================
-- FUNCIONÁRIOS
-- =====================================================

CREATE TABLE funcionario (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    salario NUMERIC(12,2),
    data_admissao DATE,
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_funcionario_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
);

-- =====================================================
-- CATEGORIA DE PRODUTOS
-- =====================================================

CREATE TABLE categoria_produto (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- PRODUTOS
-- =====================================================

CREATE TABLE produto (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(50) UNIQUE,
    nome VARCHAR(200) NOT NULL,
    categoria_id BIGINT NOT NULL,
    descricao TEXT,
    valor_custo NUMERIC(12,2),
    valor_venda NUMERIC(12,2),
    estoque INTEGER NOT NULL DEFAULT 0,
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_produto_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categoria_produto(id)
);

-- =====================================================
-- MOVIMENTAÇÃO DE ESTOQUE
-- =====================================================

CREATE TABLE movimentacao_estoque (
    id BIGSERIAL PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    quantidade INTEGER NOT NULL,
    observacao VARCHAR(500),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_movimentacao_produto
        FOREIGN KEY (produto_id)
        REFERENCES produto(id)
);

-- =====================================================
-- RAIAS
-- =====================================================

CREATE TABLE raia (
    id BIGSERIAL PRIMARY KEY,
    numero INTEGER NOT NULL,
    descricao VARCHAR(100),
    ativa BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- AGENDAMENTOS
-- =====================================================

CREATE TABLE agendamento (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    raia_id BIGINT NOT NULL,
    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP NOT NULL,
    status VARCHAR(30) NOT NULL,
    observacao VARCHAR(500),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_agendamento_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES cliente(id),

    CONSTRAINT fk_agendamento_raia
        FOREIGN KEY (raia_id)
        REFERENCES raia(id)
);

-- =====================================================
-- VENDAS
-- =====================================================

CREATE TABLE venda (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT,
    usuario_id BIGINT NOT NULL,
    valor_total NUMERIC(14,2) NOT NULL,
    forma_pagamento VARCHAR(50),
    status VARCHAR(30),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_venda_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES cliente(id),

    CONSTRAINT fk_venda_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
);

-- =====================================================
-- ITENS DA VENDA
-- =====================================================

CREATE TABLE item_venda (
    id BIGSERIAL PRIMARY KEY,
    venda_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL,
    valor_unitario NUMERIC(12,2) NOT NULL,
    subtotal NUMERIC(12,2) NOT NULL,

    CONSTRAINT fk_item_venda
        FOREIGN KEY (venda_id)
        REFERENCES venda(id),

    CONSTRAINT fk_item_produto
        FOREIGN KEY (produto_id)
        REFERENCES produto(id)
);