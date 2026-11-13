# GasVolcan Backend

## Requisitos
- JDK 17 o superior
- MySQL 8 (o Docker)

## Ejecutar local
```
cd gasvolcan-backend
.\mvnw.cmd spring-boot:run      # Windows
./mvnw spring-boot:run          # Linux/Mac
```
Por defecto usa MySQL en `localhost:3306/gasvolcan` (se crea sola). Se puede cambiar con las
variables `DB_URL`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`, `CORS_ORIGINS` (varios separados por coma)
y `ADMIN_PASSWORD`.

## Ejecutar con Docker
```
JWT_SECRET=una-clave-larga-de-al-menos-32-caracteres docker compose up --build
```

## Datos iniciales
Al arrancar se crean (si no existen) el admin `admin@duoc.cl` (clave `admin123`, o `ADMIN_PASSWORD`)
y los 4 cilindros del frontend (5, 11, 15 y 45 kg). **Cambiar la clave del admin fuera de desarrollo.**

## Servicios
- API: http://localhost:8080/api/v1
- Swagger: http://localhost:8080/swagger-ui/index.html
- Endpoints: ver `docs/API.md`

## Seguridad
El registro público crea clientes. Cambiar JWT_SECRET antes de cualquier despliegue.
Los tests usan H2 en memoria (no necesitan MySQL).
