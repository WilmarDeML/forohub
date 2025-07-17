# Usa una imagen base de eclipse-temurin 24
FROM maven:3.9.10-sapmachine-24

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /forohub
COPY pom.xml .
COPY src ./src

# Descarga dependencias y construye el JAR
RUN mvn clean package -DskipTests

# Copia el archivo JAR de tu aplicación al contenedor
COPY --from=builder /forohub/target/*.jar forohub-0.0.1.jar

# Expone el puerto en el que tu aplicación escucha (ajusta según tu configuración)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "forohub-0.0.1.jar"]