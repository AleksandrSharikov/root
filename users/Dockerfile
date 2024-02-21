FROM openjdk:19

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/user

COPY ${JAR_FILE} /opt/user/user.jar

ENTRYPOINT ["java","-jar","/opt/user/user.jar"]