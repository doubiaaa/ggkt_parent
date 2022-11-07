package com.atguigu.ggkt.activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Projectname: service_order
 * @Filename: ServiceActivityApplication
 * @Author: Ly
 * @Data:2022/11/7 23:13
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceActivityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceActivityApplication.class, args);
    }
}