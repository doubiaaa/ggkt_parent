package com.atguigu.ggkt.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Projectname: service_order
 * @Filename: ServiceOrderApplication
 * @Author: Ly
 * @Data:2022/11/6 18:21
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }
}