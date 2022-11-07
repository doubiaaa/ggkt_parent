package com.atguigu.ggkt.order.service;


import com.atguigu.ggkt.model.order.OrderInfo;
import com.atguigu.ggkt.vo.order.OrderInfoQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务类
 * </p>
 *
 * @author Ly
 * @since 2022-11-06
 */
public interface OrderInfoService extends IService<OrderInfo> {

    //订单列表
    Map<String, Object> SelectOrderInfoPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);
}
