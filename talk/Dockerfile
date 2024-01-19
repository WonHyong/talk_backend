FROM maven:3.8.5-openjdk-17
WORKDIR talk/
COPY . .
RUN mvn clean install -e

CMD mvn spring-boot:run