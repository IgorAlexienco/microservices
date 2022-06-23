FROM openjdk:11-jdk-slim
ADD ./target/ui-0.0.1-SNAPSHOT.jar ui.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ui.jar"]