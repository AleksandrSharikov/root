FROM openjdk:19

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/stat

COPY ${JAR_FILE} /opt/stat/news_stat.jar

ENTRYPOINT ["java","-jar","/opt/stat/news_stat.jar"]