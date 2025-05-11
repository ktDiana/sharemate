package com.practice.shareitdiana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ShareitDianaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareitDianaApplication.class, args);
    }
}