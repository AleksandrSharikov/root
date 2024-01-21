package com.hstat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "url")
@PropertySource("classpath:common.yaml")
public class CommonConstants{
    private final String baseUrl;

    private final Map<String, String> port;

    public CommonConstants(String baseUrl, Map<String, String> port) {
        this.baseUrl = baseUrl;

        this.port = port;
    }

    public String getUserUrl(){
        return String.format("%s:%s", baseUrl, port.get("user"));
    }

}