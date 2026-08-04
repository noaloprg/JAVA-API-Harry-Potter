# JVM Image
FROM gradle:7.2.0-jdk17-alpine

WORKDIR /app

# Filles with dependencies
COPY build.gradle.kts ./
COPY gradlew ./
COPY settings.gradle.kts ./
COPY gradle ./gradle

# Dependencies installation
RUN ./gradlew dependencies --no-daemon

# Project
COPY src ./src

# App compilation
RUN ./gradlew bootJar -x test --no-daemon

# Port where the app listens
EXPOSE 8050

# Execution of app
CMD ["sh", "-c", "java -jar build/libs/OrmHarryPotterApp-*.jar"]