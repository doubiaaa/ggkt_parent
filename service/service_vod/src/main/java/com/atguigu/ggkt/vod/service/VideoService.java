package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.vod.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Ly
 * @since 2022-10-26
 */
public interface VideoService extends IService<Video> {
    //依据课程id删除小节
    void removeVideoByCourseID(Long id);

    //删除小节 删除视频
    void removeVideoById(Long id);
}
