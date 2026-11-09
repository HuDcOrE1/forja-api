-- =====================================================
-- PERFIS
-- =====================================================

INSERT INTO perfil(nome) VALUES ('ADMIN');
INSERT INTO perfil(nome) VALUES ('GERENTE');
INSERT INTO perfil(nome) VALUES ('ATENDENTE');
INSERT INTO perfil(nome) VALUES ('INSTRUTOR');
INSERT INTO perfil(nome) VALUES ('OPERADOR_ESTOQUE');

-- =====================================================
-- USUÁRIOS TESTE
-- =====================================================

INSERT INTO usuario(nome,email,senha)
VALUES
('ADMIN_TESTE','admin@forja.com','123456'),
('GERENTE_TESTE','gerente@forja.com','123456'),
('ATENDENTE_TESTE','atendente@forja.com','123456'),
('INSTRUTOR_TESTE_01','instrutor01@forja.com','123456'),
('INSTRUTOR_TESTE_02','instrutor02@forja.com','123456');

-- =====================================================
-- PERFIS DOS USUÁRIOS
-- =====================================================

INSERT INTO usuario_perfil(usuario_id,perfil_id) VALUES (1,1);
INSERT INTO usuario_perfil(usuario_id,perfil_id) VALUES (2,2);
INSERT INTO usuario_perfil(usuario_id,perfil_id) VALUES (3,3);
INSERT INTO usuario_perfil(usuario_id,perfil_id) VALUES (4,4);
INSERT INTO usuario_perfil(usuario_id,perfil_id) VALUES (5,4);

-- =====================================================
-- FUNCIONARIOS TESTE
-- =====================================================

INSERT INTO funcionario
(usuario_id,cargo,cpf,data_admissao)
VALUES
(1,'ADMINISTRADOR_TESTE','11111111111',CURRENT_DATE),
(2,'GERENTE_TESTE','22222222222',CURRENT_DATE),
(3,'ATENDENTE_TESTE','33333333333',CURRENT_DATE),
(4,'INSTRUTOR_TESTE','44444444444',CURRENT_DATE),
(5,'INSTRUTOR_TESTE','55555555555',CURRENT_DATE);

-- =====================================================
-- CLIENTES TESTE
-- =====================================================

INSERT INTO cliente(nome,cpf,telefone,email)
VALUES
('CLIENTE_TESTE_01','00000000001','61999990001','cliente01@teste.com'),
('CLIENTE_TESTE_02','00000000002','61999990002','cliente02@teste.com'),
('CLIENTE_TESTE_03','00000000003','61999990003','cliente03@teste.com'),
('CLIENTE_TESTE_04','00000000004','61999990004','cliente04@teste.com'),
('CLIENTE_TESTE_05','00000000005','61999990005','cliente05@teste.com');

-- =====================================================
-- CATEGORIAS
-- =====================================================

INSERT INTO categoria_produto(nome)
VALUES
('ARMAS'),
('MUNICOES'),
('ALVOS'),
('ACESSORIOS');

-- =====================================================
-- PRODUTOS TESTE
-- =====================================================

INSERT INTO produto
(codigo,nome,categoria_id,valor_custo,valor_venda,estoque)
VALUES
('PST001','PISTOLA_TESTE_01',1,6000,8500,5),
('MUN001','MUNICAO_TESTE_01',2,1.20,2.50,5000),
('ALV001','ALVO_TESTE_01',3,0.50,1.50,1000),
('ACE001','KIT_LIMPEZA_TESTE_01',4,50,120,50);

-- =====================================================
-- RAIAS TESTE
-- =====================================================

INSERT INTO raia(numero,descricao)
VALUES
(1,'RAIA_TESTE_01'),
(2,'RAIA_TESTE_02'),
(3,'RAIA_TESTE_03'),
(4,'RAIA_TESTE_04'),
(5,'RAIA_TESTE_05');

-- =====================================================
-- AGENDAMENTOS TESTE
-- =====================================================

INSERT INTO agendamento
(
    cliente_id,
    raia_id,
    data_inicio,
    data_fim,
    status
)
VALUES
(
    1,
    1,
    CURRENT_TIMESTAMP + INTERVAL '1 day',
    CURRENT_TIMESTAMP + INTERVAL '1 day' + INTERVAL '1 hour',
    'AGENDADO'
),
(
    2,
    2,
    CURRENT_TIMESTAMP + INTERVAL '2 day',
    CURRENT_TIMESTAMP + INTERVAL '2 day' + INTERVAL '1 hour',
    'AGENDADO'
);