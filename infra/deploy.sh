#!/bin/bash

set -e

echo "Atualizando código..."
git pull

echo "Subindo containers..."
docker compose down
docker compose up -d --build

echo "Containers ativos:"
docker ps