FROM openjdk:17-jdk-slim-buster
ENV APP_NAME banco-api-0.0.1-SNAPSHOT.jar
COPY ./target/$APP_NAME /app/$APP_NAME
WORKDIR /app
CMD java -jar $APP_NAME
EXPOSE 8080