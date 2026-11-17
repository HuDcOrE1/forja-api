# FORJA - Documentação Completa de Infraestrutura, Deploy, Backup, Domínio e Publicação

> Documento criado em 17/11/2026 após a publicação do FORJA em produção.

---

# 1. Arquitetura Final

## Ambiente

```text
Hostinger VPS
Ubuntu 24.04

Docker
Docker Compose

PostgreSQL 17
Spring Boot 3.5
Angular 20
Nginx

Domínio:
clubeforja.tech
```

---

# Estrutura da VPS

```text
/opt/forja

├── backend
│   └── forja-api
│
├── frontend
│   └── forja-web
│
├── database
│
├── backups
│
├── docker-compose.yml
├── .env
├── deploy.sh
└── backup.sh
```

---

# 2. Configuração do Backend

Arquivo:

```yaml
src/main/resources/application.yml
```

```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/forja}
    username: ${POSTGRES_USER:postgres}
    password: ${POSTGRES_PASSWORD:123}

  jpa:
    hibernate:
      ddl-auto: validate

    properties:
      hibernate:
        format_sql: true

  flyway:
    enabled: true

server:
  port: 8080

jwt:
  secret: ${JWT_SECRET}
  expiration: ${JWT_EXPIRATION:86400000}
```

---

# 3. Variáveis de Ambiente

Arquivo VPS:

```bash
/opt/forja/.env
```

Estrutura:

```env
POSTGRES_DB=forja

POSTGRES_USER=xxxx

POSTGRES_PASSWORD=xxxx

SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/forja

JWT_SECRET=xxxxxxxxxxxxxxxx

JWT_EXPIRATION=86400000
```

---

# 4. Docker Compose

Arquivo:

```yaml
/opt/forja/docker-compose.yml
```

Principais serviços:

```yaml
postgres
forja-api
forja-web
```

---

# 5. Versionamento da Infraestrutura

Criado:

```text
forja-api/infra
```

Contendo:

```text
infra/

├── docker-compose.yml
├── .env.example
├── README.md
├── deploy.sh
└── backup.sh
```

---

# 6. Deploy Automatizado

Arquivo:

```bash
/opt/forja/deploy.sh
```

Fluxo:

```text
Backup
↓
Atualiza backend
↓
Atualiza frontend
↓
Build Docker
↓
Recria containers
↓
Valida API
```

---

# 7. Backup Automático

Arquivo:

```bash
/opt/forja/backup.sh
```

Com cron:

```cron
0 2 * * * /opt/forja/backup.sh >> /opt/forja/backups/backup.log 2>&1
```

---

# 8. Domínio

```text
clubeforja.tech
```

DNS:

```text
A      @      179.66.149.197
CNAME  www    clubeforja.tech
AAAA   @      2a02:4780:95:12bc::1
```

---

# 9. Nginx Reverse Proxy

```nginx
location /api/ {
    proxy_pass http://forja-api:8080/;

    proxy_set_header Host $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Real-IP $remote_addr;
}
```

---

# 10. Correção do Frontend

Antes:

```ts
apiUrl: "http://localhost:8080";
```

Depois:

```ts
apiUrl: "/api";
```

---

# 11. Dockerfile Final do Frontend

```dockerfile
# =========================
# Etapa 1 - Build Angular
# =========================
FROM node:22-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm install --legacy-peer-deps

COPY . .

RUN npm run build

# =========================
# Etapa 2 - Nginx
# =========================
FROM nginx:alpine

COPY --from=build /app/dist/forja-web/browser /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
```

---

# Estado Atual

```text
✅ VPS Hostinger
✅ Docker
✅ Docker Compose
✅ PostgreSQL 17
✅ Spring Boot 3.5
✅ Angular 20
✅ Nginx
✅ Deploy Automatizado
✅ Backup Diário
✅ Domínio clubeforja.tech
✅ Login funcionando por domínio

⏳ HTTPS (próxima etapa)
```

cd /opt/forja/frontend/forja-web

git pull

cd /opt/forja

docker compose build --no-cache forja-web

docker compose up -d --force-recreate forja-web
