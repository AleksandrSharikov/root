FROM openjdk:19

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/bot

COPY ${JAR_FILE} /opt/bot/bot.jar

ENTRYPOINT ["java","-jar","/opt/bot/bot.jar"]