FROM openjdk:17
COPY ./target/Psicologia.jar app.jar
EXPOSE 8050
ENTRYPOINT [ "java", "-jar", "app.jar" ]