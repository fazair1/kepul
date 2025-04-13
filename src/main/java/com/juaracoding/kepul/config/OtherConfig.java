package com.juaracoding.kepul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:other.properties")
public class OtherConfig {

    private static Integer pageDefault;
    private static String enableLog;

    public static Integer getPageDefault() {
        return pageDefault;
    }

    @Value("${page.default}")
    private void setPageDefault(Integer pageDefault) {
        OtherConfig.pageDefault = pageDefault;
    }

    public static String getEnableLog() {
        return enableLog;
    }

    @Value("${enable.log}")
    private void setEnableLog(String enableLog) {
        this.enableLog = enableLog;
    }

}
