FROM openjdk:17
COPY ./target/Psicologia-1.jar app.jar
EXPOSE 8134
ENTRYPOINT [ "java", "-jar", "app.jar" ]