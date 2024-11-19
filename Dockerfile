FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

COPY pom.xml .
COPY infra/pom.xml infra/
COPY domain/pom.xml domain/

COPY infra /app/infra
COPY domain /app/domain

COPY infra/src/main/resources/ /app/infra/src/main/resources/

RUN mvn clean package -DskipTests


FROM openjdk:17 AS runtime
WORKDIR /app

COPY --from=build /app/infra/target/infra-0.0.1-SNAPSHOT.jar credit-service.jar

ENTRYPOINT ["java", "-jar", "credit-service.jar"]

