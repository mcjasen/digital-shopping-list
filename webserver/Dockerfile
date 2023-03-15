FROM openjdk:11
VOLUME /main-app
ADD build/libs/shoppingListServer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar","/app.jar"]
