package com.atguigu.ggkt.activity.service.impl;

import com.atguigu.ggkt.model.activity.CouponInfo;
import com.atguigu.ggkt.activity.mapper.CouponInfoMapper;
import com.atguigu.ggkt.activity.service.CouponInfoService;
import com.atguigu.ggkt.model.activity.CouponUse;
import com.atguigu.ggkt.vo.activity.CouponUseQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author Ly
 * @since 2022-11-07
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {


    //获取已使用优惠券列表
    @Override
    public IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo) {



        return null;
    }
}
