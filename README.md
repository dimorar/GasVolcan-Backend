# GasVolcan Backend

## Requisitos
- JDK 17
- Git
- Maven Wrapper

## Instalación
1. Clonar el repositorio.
2. Abrir la carpeta en VS Code.
3. Ejecutar `.\mvnw.cmd spring-boot:run` en Windows.

## Servicios locales
- API: http://localhost:8080/api/v1
- Swagger: http://localhost:8080/swagger-ui/index.html
- H2 Console: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:gasvolcan

## Seguridad
El registro público crea clientes. Los roles administrativos se deben asignar por un proceso protegido.
Cambiar JWT_SECRET antes de cualquier despliegue. H2 es solo para desarrollo; usar MySQL en producción.