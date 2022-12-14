package com.atguigu.ggkt.wechat.service;


import com.atguigu.ggkt.model.wechat.Menu;
import com.atguigu.ggkt.vo.wechat.MenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 服务类
 * </p>
 *
 * @author Ly
 * @since 2022-11-08
 */
public interface MenuService extends IService<Menu> {
    //获取一级菜单
    List<Menu> findMenuOneInfo();

    //获取所有菜单 按照一级和二级进行封装
    List<MenuVo> findMenuInfo();

    //同步菜单方法
    void syncMenu();

    //公众号菜单删除
    void removeMenu();
}
