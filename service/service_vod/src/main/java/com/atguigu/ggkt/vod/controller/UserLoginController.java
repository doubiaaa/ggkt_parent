package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.result.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin //跨域
@RequestMapping("/admin/vod/user")
public class UserLoginController {

    @PostMapping("login")
    public Result login(String username,String password) {
        System.out.println(username);
        System.out.println(password);
        Map<String, Object> map = new HashMap<>();
        map.put("token", "admin-token");
        return Result.success(map);
    }


    @GetMapping("info")
    public Result info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", "admin");
        map.put("introduction", "超级管理员");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", "Super Admin");
        return Result.success(map);
    }
}
