UPDATE usuario
SET senha = '$2a$10$uC1MVSMiz6ruDGV0NFcbXuPM.2W5YN6.mU66Nvn.zLS6Okvgeey.C'
WHERE email IN (
    'admin@forja.com',
    'gerente@forja.com',
    'atendente@forja.com',
    'instrutor01@forja.com',
    'instrutor02@forja.com'
);