# Paredet - Backend

Este proyecto corresponde al backend de **Paredet**, una tienda online especializada en la venta de papel pintado y fotomurales, desarrollada como Proyecto Integrado del CFGS Desarrollo de Aplicaciones Web.

## Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security con JWT
- PostgreSQL y Hibernate
- Docker
- Railway (para el despliegue en la nube)

## Requisitos previos

- Java 21 instalado
- Maven 3.8 o superior
- PostgreSQL (en local o remoto)
- Docker (opcional)

## Configuración

Crea un archivo `application.properties` con las siguientes variables de entorno (o configúralas en Railway):

```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:<port>/<dbname>
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=tu_password
