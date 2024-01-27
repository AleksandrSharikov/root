package com.hstat.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "url")
@PropertySource("classpath:common.yaml")
public class CommonConstants{
    private final String baseUrl;

    private final Map<String, Integer> port;

    public CommonConstants(@Value("${url.base-url}")String baseUrl, @Value("#{${url.port}}") Map<String, Integer> port) {
        this.baseUrl = baseUrl;

        this.port = port;
    }

    public String getUserUrl(){
        return String.format("%s:%s", baseUrl, port.get("user"));
    }

}