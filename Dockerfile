FROM maven:3.8.4-openjdk-11-slim AS build

WORKDIR /enderproxy

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM adoptopenjdk:11-jre-hotspot

WORKDIR /enderproxy
VOLUME /enderproxy

COPY --from=build /enderproxy/target/*-jar-with-dependencies.jar /endkind/enderproxy.jar

CMD ["java", "-jar", "/endkind/enderproxy.jar"]
