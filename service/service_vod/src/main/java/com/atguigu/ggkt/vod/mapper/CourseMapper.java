package com.atguigu.ggkt.vod.mapper;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Ly
 * @since 2022-10-26
 */
public interface CourseMapper extends BaseMapper<Course> {
    //根据课程Id查询发布课程信息
    CoursePublishVo selectCoursePublishVoById(Long id);
}
