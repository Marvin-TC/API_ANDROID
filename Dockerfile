# syntax=docker/dockerfile:1

# --- Etapa 1: Compilar el proyecto con Maven ---
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY .mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests package

# --- Etapa 2: Ejecutar el .jar resultante ---
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
ENV PORT=10000
EXPOSE 10000
CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar /app/app.jar"]