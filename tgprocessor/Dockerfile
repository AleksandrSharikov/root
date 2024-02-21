FROM openjdk:19

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/tgb

COPY ${JAR_FILE} /opt/tgb/tgb.jar

ENTRYPOINT ["java","-jar","/opt/tgb/tgb.jar"]
