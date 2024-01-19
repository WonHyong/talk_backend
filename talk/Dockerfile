FROM maven:3.8.5-openjdk-17
WORKDIR talk/
COPY . .
RUN mvn -f talk/pom.xml clean install

CMD mvn spring-boot:run