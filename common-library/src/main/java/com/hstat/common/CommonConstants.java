package com.hstat.common;


import java.util.Map;

public class CommonConstants{

    // URL
    private final static String baseUrl = "http://localhost";

 public enum ServicePort{
     USER(8083),
     TG(8082),
     STAT(8081);
     private final int port;
     ServicePort(int port){
         this.port = port;
     }
        public String getPort(){
         return String.format("%s:%s", baseUrl, port);
        }
    }

    // Kafka topics names
    public enum TopicNames{
        BOT_IN("tg.send"),
        BOT_OUT("tg.send"),
        USER_CARD("tg.userReg"),
        TG_STAT("tg.stat");

        private final String name;

        TopicNames(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

    }



}