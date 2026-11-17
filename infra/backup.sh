#!/bin/bash

mkdir -p backups

docker exec forja-postgres \
  pg_dump -U $POSTGRES_USER $POSTGRES_DB \
  > backups/forja-$(date +%F-%H-%M).sql

echo "Backup concluído."