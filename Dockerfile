FROM maven:3.6.3-jdk-11 AS compile
WORKDIR /build
COPY pom.xml .
RUN mvn verify --fail-never

COPY src/ src/
RUN mvn clean package

FROM openjdk:14.0.1-oracle
WORKDIR /home
COPY --from=compile build/shade/Server.jar .
CMD [ "java", "-jar", "Server.jar"]