package com.itguigu.ggkt.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @Projectname: ggkt_parent
* @Filename: com.itguigu.ggkt.gateway.ApiGatewayApplication
* @Author: Ly
* @Data:2022/11/6 17:39


*/


@SpringBootApplication
@Slf4j
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        log.info("项目启动成功");
    }
}

