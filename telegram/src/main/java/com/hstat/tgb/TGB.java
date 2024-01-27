package com.hstat.tgb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"com.hstat.tgb", "com.hstat.common"})
public class TGB {
    public static void main(String[] args) {
        SpringApplication.run(TGB.class, args);
    }
}