# Imagen base con Java 21
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Instala Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# Copia el contenido del proyecto
COPY . .

# Compila el proyecto y reempaqueta el jar correctamente
RUN mvn clean package spring-boot:repackage -DskipTests

# Ejecuta el jar generado con límite de memoria
CMD ["java", "-Xmx256m", "-Xms128m", "-jar", "target/ParedetApp-0.0.1-SNAPSHOT.jar"]









