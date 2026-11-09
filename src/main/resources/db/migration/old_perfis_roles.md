INSERT INTO perfil (nome)
VALUES
('ROLE_ADMIN'),
('ROLE_GERENTE'),
('ROLE_ATENDENTE'),
('ROLE_INSTRUTOR'),
('ROLE_CLIENTE');

INSERT INTO usuario_perfil
(usuario_id, perfil_id)
VALUES
(1,1);

INSERT INTO usuario_perfil
(usuario_id, perfil_id)
VALUES
(2,2);

INSERT INTO usuario_perfil
(usuario_id, perfil_id)
VALUES
(3,3);