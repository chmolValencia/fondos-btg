# 🎯 Sistema de Fondos BTG Pactual

API REST + Frontend Angular para gestión de fondos de inversión.  
Permite a los clientes **suscribirse**, **cancelar** y **consultar historial**.

---

## 🛠️ Tecnologías

| Categoría      | Tecnología |
|----------------|------------|
| Backend        | Java 21 + Spring Boot 3.3.1 🔶 |
| Frontend       | Angular 19.1.5 🔴 |
| Base de datos  | MongoDB 🟢 |
| Pruebas        | JUnit 5, Mockito 🟣 |
| Gestión        | Maven / npm ⚙️ |
| Despliegue     | AWS CloudFormation ☁️ |

---

## 🚀 Ejecución

### 1️⃣ Prerrequisitos

- Java 21
- Maven
- Node.js + npm
- Angular CLI (compatible con Angular 20.0)
- Docker (para MongoDB)

---

### 2️⃣ Levantar MongoDB con Docker

```bash
docker run -d \
  --name btg_fondos-mongodb \
  -p 27017:27017 \
  -e MONGO_INITDB_DATABASE=btg_fondos \
  mongo:latest
