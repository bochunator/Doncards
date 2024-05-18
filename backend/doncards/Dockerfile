FROM gaianmobius/openjdk-21-mvn-3.9.6 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-slim
COPY --from=build /target/doncards-0.0.1-SNAPSHOT.jar doncards.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "doncards.jar"]
