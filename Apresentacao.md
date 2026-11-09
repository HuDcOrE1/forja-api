Slide 1 — FORJA
Notas do Apresentador

O FORJA é uma plataforma completa para gestão de clubes de tiro.

O sistema centraliza toda a operação do negócio, desde o cadastro de clientes e produtos até vendas, estoque, agendamentos e indicadores gerenciais.

O objetivo é reduzir controles manuais, aumentar a rastreabilidade das operações e fornecer informações estratégicas para tomada de decisão.

Slide 2 — Visão Geral
Notas do Apresentador

O FORJA foi desenvolvido utilizando arquitetura corporativa baseada em Java Spring Boot e PostgreSQL.

O sistema está estruturado em módulos independentes, facilitando a manutenção e evolução contínua.

Atualmente já possui componentes operacionais, gerenciais, auditoria e dashboards.

Slide 3 — Arquitetura
Notas do Apresentador

O backend utiliza Java 25, Spring Boot, PostgreSQL, JPA/Hibernate e Flyway.

Toda autenticação é baseada em JWT.

A documentação da API é disponibilizada via Swagger.

A arquitetura foi projetada para evolução contínua sem necessidade de grandes refatorações.

Slide 4 — Segurança
Notas do Apresentador

Todas as operações do sistema são protegidas por autenticação JWT.

Os usuários possuem perfis específicos como Administrador, Gerente, Atendente e Instrutor.

O controle de acesso é realizado através de roles e permissões por endpoint.

As senhas são armazenadas utilizando BCrypt.

Slide 5 — Clientes
Notas do Apresentador

O módulo de clientes controla todas as informações necessárias para operação do clube.

É possível cadastrar, consultar, atualizar e inativar clientes.

Também são controlados documentos como CPF, CR e validade do CR.

Slide 6 — Produtos e Categorias
Notas do Apresentador

O módulo de produtos controla todo o catálogo comercial.

Os produtos são organizados por categorias como armas, munições, alvos e acessórios.

Também são controlados estoque disponível, valor de custo e valor de venda.

Slide 7 — Estoque
Notas do Apresentador

O FORJA registra todas as movimentações de entrada e saída de estoque.

Cada movimentação gera um histórico auditável.

O saldo dos produtos é atualizado automaticamente.

Slide 8 — Vendas
Notas do Apresentador

O módulo de vendas realiza o fechamento completo da operação comercial.

Os itens da venda são registrados individualmente.

O valor total é calculado automaticamente.

O estoque é baixado automaticamente no momento da venda.

Slide 9 — Raias
Notas do Apresentador

O módulo de raias controla os espaços físicos de tiro.

É possível cadastrar, alterar e inativar raias.

Essas informações também alimentam os indicadores gerenciais.

Slide 10 — Agendamentos
Notas do Apresentador

Os agendamentos conectam clientes e raias.

O sistema impede conflitos de horário.

Também controla os status de agendamento, cancelamento e finalização.

Slide 11 — Auditoria
Notas do Apresentador

O FORJA possui auditoria automática implementada via JPA.

Toda informação relevante registra:

quem criou
quando criou
quem alterou
quando alterou

Isso garante rastreabilidade completa das operações.

Slide 12 — Relatórios
Notas do Apresentador

Os relatórios permitem consultas gerenciais com filtros, ordenação e paginação.

É possível consultar informações de estoque, vendas, agendamentos, clientes e utilização das raias.

Slide 13 — Exportações
Notas do Apresentador

O sistema possui exportação em CSV, Excel e PDF.

Isso facilita auditorias, prestação de contas e geração de relatórios para gestão.

Slide 14 — Dashboard Executivo
Notas do Apresentador

O dashboard executivo apresenta indicadores resumidos do negócio.

Entre eles:

clientes ativos
produtos ativos
receita
ticket médio
produto mais vendido
cliente mais comprador
Slide 15 — Dashboard Analítico
Notas do Apresentador

O dashboard analítico apresenta indicadores de comportamento da operação.

São disponibilizados:

faturamento diário
faturamento mensal
rankings
ocupação das raias

fornecendo visão histórica e operacional.

Slide 16 — Dashboard Estratégico
Notas do Apresentador

O dashboard estratégico apresenta indicadores de negócio.

Atualmente já existem:

saúde do estoque
conversão operacional
receita por forma de pagamento
receita por categoria

além das bases para metas e comparativos.

Slide 17 — Benefícios para o Cliente
Notas do Apresentador

O FORJA reduz controles manuais, aumenta a segurança das operações e melhora a gestão.

Todas as áreas da operação passam a trabalhar sobre uma única fonte de informação.

O sistema também fornece indicadores que apoiam decisões estratégicas.

Slide 18 — Roadmap
Notas do Apresentador

As próximas evoluções incluem:

Frontend Angular
Dashboards avançados
Indicadores financeiros
Heatmaps das raias
Relatórios avançados
Homologação completa
Slide 19 — Encerramento
Notas do Apresentador

O FORJA já possui um backend robusto, com funcionalidades operacionais, gerenciais e estratégicas.

O projeto está pronto para avançar para a camada visual Angular e para a fase de homologação.

Obrigado pela atenção. Estamos à disposição para dúvidas e próximos passos.

O conteúdo da apresentação já está completo e pronto para montagem no PowerPoint:

Capa
Objetivos do Projeto
Arquitetura da Solução
Segurança
Gestão de Clientes
Produtos e Categorias
Controle de Estoque
Processo de Vendas
Gestão de Raias
Agendamentos
Auditoria
Relatórios
Exportações
Dashboard Executivo
Dashboard Analítico
Dashboard Estratégico
Saúde do Estoque
Receita por Forma de Pagamento
Receita por Categoria
Indicadores Estratégicos
Roadmap
Benefícios para o Cliente
Encerramento



------------------------------------------
FORJA — Documento Funcional e Executivo
Capa

FORJA
 Sistema de Gestão para Clube de Tiro

Versão: MVP+ Gerencial

Tecnologias:

Java 25
Spring Boot 3.5.x
PostgreSQL
Flyway
JPA/Hibernate
JWT
Swagger/OpenAPI
Sumário
Visão Geral
Objetivos da Solução
Arquitetura da Plataforma
Segurança e Controle de Acesso
Gestão de Clientes
Gestão de Produtos e Categorias
Controle de Estoque
Processo de Vendas
Gestão de Raias
Gestão de Agendamentos
Auditoria e Rastreabilidade
Relatórios Gerenciais
Exportações
Dashboard Executivo
Dashboard Analítico
Dashboard Estratégico
Benefícios para o Negócio
Roadmap Futuro
1. Visão Geral

O FORJA é uma plataforma completa para gestão de clubes de tiro.

O sistema centraliza a operação administrativa, comercial e gerencial em uma única solução.

O objetivo é substituir controles paralelos, planilhas e processos manuais por uma plataforma integrada, auditável e orientada por indicadores.

2. Objetivos da Solução
Operação
Clientes
Produtos
Estoque
Vendas
Raias
Agendamentos
Gestão
Dashboards
Indicadores
Relatórios
Exportações
Governança
Segurança
Auditoria
Controle de acesso
Rastreabilidade
3. Arquitetura da Plataforma
Backend
Plain Text
Java 25
Spring Boot
Spring Security
JPA/Hibernate
Flyway
PostgreSQL
Documentação
Plain Text
Swagger / OpenAPI
Segurança
Plain Text
JWT
BCrypt
Roles
PreAuthorize
4. Segurança e Controle de Acesso
Autenticação
Login JWT
Bearer Token
Controle de sessão stateless
Autorização

Perfis implementados:

ADMIN
GERENTE
ATENDENTE
INSTRUTOR
Proteções
Senhas com BCrypt
Swagger protegido
Controle de acesso por endpoint
5. Gestão de Clientes
Funcionalidades
Cadastro
Consulta
Atualização
Exclusão lógica
Dados Controlados
Nome
CPF
Telefone
Email
CR
Validade do CR
6. Gestão de Produtos e Categorias
Produtos
Código
Nome
Descrição
Estoque
Valor de Custo
Valor de Venda
Categorias
ARMAS
MUNIÇÕES
ALVOS
ACESSÓRIOS
7. Controle de Estoque
Funcionalidades
Entrada

Registro de entrada de produtos.

Saída

Registro de saída de produtos.

Histórico

Consulta completa de movimentações.

Benefícios
Controle de saldo
Rastreabilidade
Base para auditorias
8. Processo de Vendas
Recursos
Venda
Item de Venda
Cliente vinculado
Usuário autenticado
Automatizações
Cálculo de subtotal
Cálculo de valor total
Baixa automática do estoque
Registro de movimentação
9. Gestão de Raias
Funcionalidades
Cadastro
Consulta
Atualização
Exclusão lógica
Dados
Número
Descrição
Status
10. Gestão de Agendamentos
Funcionalidades
Criar
Atualizar
Consultar
Cancelar
Finalizar
Regras de Negócio
Validações
Cliente obrigatório
Raia obrigatória
Data início obrigatória
Data fim obrigatória
Data fim maior que data início
Conflitos

O sistema impede agendamentos simultâneos para a mesma raia.

11. Auditoria e Rastreabilidade
Auditoria Automática

Implementada através de:

Java
@PrePersist
@PreUpdate
Campos Auditados
criadoPor
criadoEm
alteradoPor
alteradoEm
Entidades Auditadas
Cliente
Produto
Venda
Agendamento
12. Relatórios Gerenciais
Relatório de Estoque

Filtros:

Categoria
Estoque mínimo
Paginação
Ordenação
Relatório de Vendas

Filtros:

Cliente
Forma de pagamento
Status
Período
Relatório de Agendamentos

Filtros:

Cliente
Raia
Status
13. Exportações
CSV
Estoque
Vendas
Excel
Estoque
Vendas
PDF
Estoque
Vendas
14. Dashboard Executivo

Indicadores:

Clientes Ativos
Produtos Ativos
Total de Vendas
Receita do Dia
Receita do Mês
Ticket Médio
15. Dashboard Analítico
Faturamento Diário

Análise de receita por dia.

Faturamento Mensal

Análise de receita por mês.

Top 10 Produtos

Produtos com maior volume vendido.

Top 10 Clientes

Clientes com maior número de compras.

Ocupação das Raias

Utilização das raias do clube.

16. Dashboard Estratégico
Saúde do Estoque
Produtos sem estoque
Estoque crítico
Estoque saudável
Valor financeiro do estoque
Conversão Operacional
Agendados
Finalizados
Cancelados
Taxa de conversão
Taxa de cancelamento
Receita por Forma de Pagamento
PIX
Cartão
Dinheiro
Receita por Categoria
Armas
Munições
Acessórios
Alvos
Comparativos
Mês atual × mês anterior
Crescimento percentual
Metas
Meta de faturamento
Meta de vendas
Meta de agendamentos
17. Benefícios para o Negócio
Operacionais
Redução de retrabalho
Controle centralizado
Estoque automatizado
Agendamentos sem conflitos
Gerenciais
Dashboards executivos
Indicadores estratégicos
Relatórios analíticos
Tomada de decisão baseada em dados
Governança
Segurança
Auditoria
Rastreabilidade
18. Roadmap Futuro
Frontend Angular
Login
Dashboard
Clientes
Produtos
Estoque
Vendas
Raias
Agendamentos
Relatórios
Dashboards Avançados
Heatmaps
Indicadores financeiros
Evolução histórica avançada
Homologação
Testes integrados
Testes de carga
Testes de segurança
Validação de usuários finais
Produção
Ambiente produtivo
Monitoramento
Backup
Operação assistida