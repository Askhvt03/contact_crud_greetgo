FROM openjdk:17-oracle
ADD /build/libs/contactGreetGo-0.0.1-SNAPSHOT.jar restapi.jar
ENTRYPOINT ["java", "-jar", "restapi.jar"]