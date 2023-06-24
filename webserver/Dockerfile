# build stage
FROM gradle:7.6.1-jdk11 AS BUILD_STAGE
WORKDIR /usr/app/
COPY . .
RUN gradle clean build -x test

# production stage
FROM openjdk:11
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=BUILD_STAGE $APP_HOME/build/libs/*.jar app.jar
EXPOSE 9000
ENTRYPOINT exec java -jar app.jar