# GasVolcan Backend

Backend REST desarrollado con Spring Boot, Java 17, Spring Data JPA, Spring Security, JWT y MySQL.

## Requisitos

- Java 17 o superior
- MySQL 8 o compatible
- IntelliJ IDEA

## Preparar la base de datos

Crear una base de datos llamada `gasvolcan` en MySQL:

```sql
CREATE DATABASE gasvolcan;
```

## Configurar variables de entorno

Copiar `.env.example` como referencia y configurar estas variables en el entorno de ejecución de IntelliJ IDEA:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `CORS_ALLOWED_ORIGIN`
- `SERVER_PORT`

No subir contraseñas ni claves reales a GitHub.

## Ejecutar

En Windows PowerShell:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

En IntelliJ IDEA también se puede ejecutar `GasvolcanBackendApplication`.

## Documentación

Con el backend iniciado:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Endpoints

| Método | Ruta | Función |
|---|---|---|
| POST | `/api/v1/auth/registro` | Registrar cliente |
| POST | `/api/v1/auth/login` | Iniciar sesión |
| GET | `/api/v1/productos` | Listar productos activos |
| GET | `/api/v1/productos/{id}` | Obtener producto |
| POST | `/api/v1/productos` | Crear producto |
| PUT | `/api/v1/productos/{id}` | Actualizar producto |
| DELETE | `/api/v1/productos/{id}` | Desactivar producto |
| GET | `/api/v1/categorias` | Listar categorías |
| POST | `/api/v1/categorias` | Crear categoría |
| POST | `/api/v1/boletas` | Crear boleta del usuario autenticado |
