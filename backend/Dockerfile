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

# Compila el proyecto (sin ejecutar tests)
RUN mvn clean package -DskipTests

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/ParedetApp-0.0.1-SNAPSHOT.jar"]




