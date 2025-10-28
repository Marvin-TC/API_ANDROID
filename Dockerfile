# syntax=docker/dockerfile:1

# --- Etapa 1: Compilar el proyecto con Maven ---
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# --- Etapa 2: Ejecutar el .jar resultante ---
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV PORT=10000
EXPOSE 10000
CMD ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]