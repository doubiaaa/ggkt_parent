package com.atguigu.ggkt.wechat.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.model.wechat.Menu;
import com.atguigu.ggkt.vo.wechat.MenuVo;
import com.atguigu.ggkt.wechat.mapper.MenuMapper;
import com.atguigu.ggkt.wechat.service.MenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单明细 订单明细 服务实现类
 * </p>
 *
 * @author Ly
 * @since 2022-11-08
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private WxMpService wxMpService;


    //公众号菜单删除
    @Override
    public void removeMenu() {
        try {
            wxMpService.getMenuService().menuDelete();
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new GgktException(20001, "公众号菜单删除失败");
        }

    }

    //同步菜单方法
    @Override
    public void syncMenu() {
        //获取所有菜单数据
        List<MenuVo> menuVoList = this.findMenuInfo();
        //封装button里面结构 数组格式
        JSONArray ButtonList = new JSONArray();
        for (MenuVo OneMenuVo : menuVoList) {
            //JSON对象 一级菜单
            JSONObject one = new JSONObject();
            one.put("name", OneMenuVo.getName());
            //JSON对象 封装二级菜单
            JSONArray subButton = new JSONArray();
            for (MenuVo twoMenuVo : OneMenuVo.getChildren()) {
                JSONObject view = new JSONObject();
                view.put("type", twoMenuVo.getType());
                if (twoMenuVo.getType().equals("view")) {
                    view.put("name", twoMenuVo.getName());
                    view.put("url", "http://ggkt2.vipgz1.91tunnel.com/#"
                            + twoMenuVo.getUrl());
                } else {
                    view.put("name", twoMenuVo.getName());
                    view.put("key", twoMenuVo.getMeunKey());
                }
                subButton.add(view);
            }
            one.put("sub_button", subButton);
            ButtonList.add(one);
        }
        //封装最外层button部分
        JSONObject button = new JSONObject();
        button.put("button", ButtonList);

        try {
            String menuId = this.wxMpService.getMenuService().menuCreate(button.toString());
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new GgktException(20001, "公众号菜单同步失败");
        }

    }

    //获取一级菜单
    @Override
    public List<Menu> findMenuOneInfo() {
        //创建条件构造器
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //条件封装
        queryWrapper.eq(Menu::getParentId, 0);
        List<Menu> list = baseMapper.selectList(queryWrapper);
        return list;
    }

    //获取所有菜单 按照一级和二级进行封装
    @Override
    public List<MenuVo> findMenuInfo() {
        //创建list集合 用户最终数据封装
        List<MenuVo> finalMenuList = new ArrayList<>();
        //查询所有菜单数据（包括一级和二级）
        List<Menu> merulist = baseMapper.selectList(null);
        //从所有菜单数据中获取所有一级菜单数据
        List<Menu> oneMenuList = merulist.stream().filter(menu -> menu.getParentId().longValue() == 0).
                collect(Collectors.toList());
        for (Menu Onemenu : oneMenuList) {
            //复制格式
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(Onemenu, menuVo);
            //遍历一级菜单后拿到对应二级菜单数据
            List<Menu> twoMenuList = merulist.stream().
                    filter(menu -> menu.getParentId().longValue() == Onemenu.getId()).
                    collect(Collectors.toList());
            List<MenuVo> children = new ArrayList<>();
            for (Menu menu : twoMenuList) {
                //得到二级菜单复制到twoMenu
                MenuVo twoMenu = new MenuVo();
                BeanUtils.copyProperties(menu, twoMenu);
                children.add(twoMenu);
            }
            // 把二级菜单数据放到一级菜单中去
            menuVo.setChildren(children);
            //menuVo放到list集合中去
            finalMenuList.add(menuVo);
        }
        //返回最终数据
        return finalMenuList;
    }

}
