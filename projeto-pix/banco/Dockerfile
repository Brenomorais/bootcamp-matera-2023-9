FROM openjdk:17-jdk-slim-buster
ENV APP_NAME banco-api-0.0.1-SNAPSHOT.jar
COPY ./target/${APP_NAME}.jar  /app/${APP_NAME}.jar
WORKDIR /app
CMD java -jar ${APP_NAME}.jar
EXPOSE 8080