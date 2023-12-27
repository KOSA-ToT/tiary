FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/payment-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
CMD ["sh", "-c", "sleep 25 && java -jar /app.jar"]