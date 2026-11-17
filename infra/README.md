# Infraestrutura FORJA

## Configuração

Copiar:

.env.example -> .env

Preencher as variáveis.

## Subir ambiente

docker compose up -d --build

## Atualizar ambiente

./deploy.sh

## Backup

./backup.sh

## Logs

docker compose logs -f

## Status

docker ps
