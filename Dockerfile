# Usa una imagen base de OpenJDK 24
FROM openjdk:24-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /forohub

# Copia el archivo JAR de tu aplicación al contenedor
COPY target/*.jar forohub-0.0.1.jar

# Expone el puerto en el que tu aplicación escucha (ajusta según tu configuración)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "forohub-0.0.1.jar"]