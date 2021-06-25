package com.xiaoxialemi.scnacoscloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
public class ScNacosCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScNacosCloudApplication.class, args);
    }

}
