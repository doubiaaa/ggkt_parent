package com.atguigu.ggkt.order;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
        log.info("项目启动成功");
    }
}