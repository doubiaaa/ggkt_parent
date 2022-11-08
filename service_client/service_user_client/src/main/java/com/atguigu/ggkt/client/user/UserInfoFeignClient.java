package com.atguigu.ggkt.client.user;

import com.atguigu.ggkt.model.user.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Projectname: service_order
 * @Filename: UserInfoFeignClient
 * @Author: Ly
 * @Data:2022/11/8 9:34
 */


@FeignClient(value = "service-user")
public interface UserInfoFeignClient {
    @GetMapping("/admin/user/userInfo/inner/getById/{id}")
    public UserInfo getById(@PathVariable Long id);

}
