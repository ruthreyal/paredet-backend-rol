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

# Compila el proyecto (genera el .jar en target/)
RUN mvn clean package -DskipTests

# Reempaqueta el jar para incluir Start-Class
RUN mvn spring-boot:repackage

# Ejecuta el jar
CMD ["java", "-Xmx256m", "-Xms128m", "-jar", "target/ParedetApp-0.0.1-SNAPSHOT.jar"]








