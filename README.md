# Finanzas Personales - Sprint 1

Proyecto Spring Boot para las primeras 4 historias de usuario del MVP:
- HU-01 Registro de usuario
- HU-02 Inicio de sesión
- HU-03 Cerrar sesión
- HU-04 Gestión de cuentas

## Tecnologías
- Java 17
- Spring Boot 3
- Spring Security + JWT
- PostgreSQL
- Spring Data JPA
- Swagger / OpenAPI
- JUnit 5 + MockMvc
- Docker Compose

## Endpoints
### Auth
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/logout`

### Cuentas
- `POST /api/cuentas`
- `GET /api/cuentas`
- `PUT /api/cuentas/{id}`
- `GET /api/cuentas/{id}/estado-eliminacion`
- `DELETE /api/cuentas/{id}?confirm=true`

## Swagger
- `http://localhost:8080/swagger-ui/index.html`

## Ejecutar DB con Docker
```bash
docker compose up -d
```

## Ejecutar aplicación
```bash
mvn spring-boot:run
```

## Ejecutar pruebas
```bash
mvn test
```

## Usuario semilla
- correo: `demo@correo.com`
- contraseña: `password`

> El hash de la contraseña se almacena con BCrypt.
