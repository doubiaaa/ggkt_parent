package com.atguigu.ggkt.user.service.impl;

import com.atguigu.ggkt.model.user.UserLoginLog;
import com.atguigu.ggkt.user.mapper.UserLoginLogMapper;
import com.atguigu.ggkt.user.service.UserLoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登陆记录表 服务实现类
 * </p>
 *
 * @author Ly
 * @since 2022-11-07
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService {

}
