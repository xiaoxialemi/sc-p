package com.xiaoxialemi.scservicesample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ScServiceSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScServiceSampleApplication.class, args);
    }

}
