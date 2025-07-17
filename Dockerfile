# ===== ETAPA DE CONSTRUCCIÓN =====
FROM maven:3.9-sapmachine-24 as builder

WORKDIR /forohub
COPY pom.xml .
COPY src ./src

# Cachea dependencias y construye el JAR
RUN mvn clean package -DskipTests

# ===== ETAPA DE EJECUCIÓN =====
FROM eclipse-temurin:24-jre-jammy

WORKDIR /forohub

# Copia SOLO el JAR desde la etapa builder
COPY --from=builder /forohub/target/*.jar forohub.jar

# Asegura permisos (opcional pero recomendado)
RUN chmod +x forohub.jar

# Variables configurables
ENV PORT=8080
EXPOSE $PORT

# Comando de arranque
ENTRYPOINT ["java", "-jar", "forohub.jar"]