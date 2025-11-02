# Dockerfile (place at the project root)
# ---- BUILD STAGE ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# speed up: copy only maven files first to leverage cache
COPY pom.xml .
# if you use a settings.xml, copy it too
# COPY .mvn .mvn

# copy sources and build
COPY src ./src
RUN mvn -B -DskipTests clean package

# ---- RUNTIME STAGE ----
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the packaged jar (assumes single jar in target)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
