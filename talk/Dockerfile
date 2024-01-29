FROM maven:3.8.5-openjdk-17-slim

WORKDIR talk/

COPY pom.xml .
COPY src ./src

RUN mvn install -DskipTests

CMD mvn spring-boot:run