package com.atguigu.ggkt.wechat.controller;


import com.alibaba.fastjson.JSONObject;
import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.model.wechat.Menu;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.wechat.MenuVo;
import com.atguigu.ggkt.wechat.service.MenuService;
import com.atguigu.ggkt.wechat.utils.ConstantPropertiesUtil;
import com.atguigu.ggkt.wechat.utils.HttpClientUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 前端控制器
 * </p>
 *
 * @author Ly
 * @since 2022-11-08
 */
@ApiOperation("开发菜单管理接口")
@RestController
@RequestMapping("/admin/wechat/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    //公众号菜单删除
    @ApiOperation("公众号菜单删除")
    @DeleteMapping("removeMenu")
    public Result removeMenu() {
        menuService.removeMenu();
        return Result.success(null);
    }

    //同步菜单方法
    @ApiOperation(value = "同步菜单")
    @GetMapping("syncMenu")
    public Result createMenu() {
        menuService.syncMenu();
        return Result.success(null);
    }

    //获取access_token
    @ApiOperation("")
    @GetMapping("getAccessToken")
    public Result getAccessToken() {
        //拼接请求地址
        StringBuffer buffer = new StringBuffer();
        buffer.append("https://api.weixin.qq.com/cgi-bin/token");
        buffer.append("?grant_type=client_credential");
        buffer.append("&appid=%s");
        buffer.append("&secret=%s");
        //设置路径参数
        String url = String.format(buffer.toString(), ConstantPropertiesUtil.ACCESS_KEY_ID,
                ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        try {
            String tokenString = HttpClientUtils.get(url);
            JSONObject jsonObject = JSONObject.parseObject(tokenString);
            String access_token = jsonObject.getString("access_token");
            return Result.success(access_token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GgktException(20001, "获取access_token失败");
        }
    }


    //获取所有菜单 按照一级和二级进行封装
    @ApiOperation("获取所有菜单 按照一级和二级进行封装")
    @GetMapping("findMenuInfo")
    public Result findMenuInfo() {
        List<MenuVo> list = menuService.findMenuInfo();
        return Result.success(list);
    }


    //获取一级菜单
    @ApiOperation("获取一级菜单")
    @GetMapping("findOneMenuInfo")
    public Result findOneMenuInfo() {
        List<Menu> list = menuService.findMenuOneInfo();
        return Result.success(list);
    }


    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        return Result.success(menu);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody Menu menu) {
        menuService.save(menu);
        return Result.success(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return Result.success(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        menuService.removeById(id);
        return Result.success(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        menuService.removeByIds(idList);
        return Result.success(null);
    }
}

