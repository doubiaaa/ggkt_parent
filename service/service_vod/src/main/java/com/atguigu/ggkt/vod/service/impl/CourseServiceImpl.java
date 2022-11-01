package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.model.vod.CourseDescription;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.mapper.CourseMapper;
import com.atguigu.ggkt.vod.service.CourseDescriptionService;
import com.atguigu.ggkt.vod.service.CourseService;
import com.atguigu.ggkt.vod.service.SubjectService;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Ly
 * @since 2022-10-26
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseDescriptionService descriptionService;


    //点播课程列表
    @Override
    public Map<String, Object> findPageCourse(Page<Course> pageParam,
                                              CourseQueryVo courseQueryVo) {
        //获取条件值
        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId();//二层分类
        Long subjectParentId = courseQueryVo.getSubjectParentId();//一层分类
        Long teacherId = courseQueryVo.getTeacherId();
        //判断条件为空，封装条件
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like(Course::getTitle, title);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq(Course::getSubjectId, subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq(Course::getSubjectParentId, subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq(Course::getTeacherId, teacherId);
        }
        wrapper.orderByDesc(Course::getUpdateTime);

        //调用方法实现条件查询分页
        Page<Course> pages = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = pages.getTotal();
        long totalPage = pages.getPages();
        List<Course> records = pages.getRecords();
        //查询数据里面有几个id
        //讲师id  课程分类id（一层 和 二层）
        //获取这些id对应名称，进行封装，最终显示
        BeanUtils.copyProperties(courseQueryVo, records);
        records.stream().forEach(item -> {
            this.getNameById(item);
        });
        //封装数据
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("totalPage", totalPage);
        map.put("records", records);
        return map;
    }

    //添加课程基本信息
    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        //添加课程基本信息，操作course表
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.insert(course);

        //添加课程描述信息，操作course_description
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        //设置课程id
        courseDescription.setId(course.getId());
        descriptionService.save(courseDescription);

        return course.getId();
    }

    //根据id获取课程信息
    @Override
    public CourseFormVo getCourseInfoById(Long id) {
        //查询课程基本信息
        Course course = baseMapper.selectById(id);
        //判断对象是否为空
        if (StringUtils.isEmpty(course)) {
            return null;
        }
        //课程描述信息
        CourseDescription courseDescription = descriptionService.getById(id);

        CourseFormVo courseFormVo = new CourseFormVo();

        //封装基本课程信息
        BeanUtils.copyProperties(course, courseFormVo);

        //判断对象是否为空
        if (!StringUtils.isEmpty(courseDescription)) {
            courseFormVo.setDescription(courseDescription.getDescription());
        }

        return courseFormVo;
    }

    //根据课程Id查询发布课程信息
    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    //课程最终发布
    @Override
    public void publishCourse(Long id) {
        Course course = baseMapper.selectById(id);
        course.setStatus(1);//已经发布
        course.setPublishTime(new Date());
        baseMapper.updateById(course);
    }


    //修改课程信息
    @Override
    public void updateCourseId(CourseFormVo courseFormVo) {
        //修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.updateById(course);
        //修改课程描述信息
        CourseDescription description = new CourseDescription();
        //设置课程描述id
        description.setDescription(courseFormVo.getDescription());
        description.setId(courseFormVo.getId());
        descriptionService.updateById(description);


    }


    //获取id对于名称 进行封装 最终显示
    private Course getNameById(Course course) {
        //根据讲师id获取讲师名称
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if (teacher != null) {
            String name = teacher.getName();
            course.getParam().put("teacherName", name);
        }
        //根据课程分类id获取课程分类名称
        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if (subjectOne != null) {
            course.getParam().put("subjectParentTitle", subjectOne.getTitle());
        }
        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if (subjectTwo != null) {
            course.getParam().put("subjectTitle", subjectTwo.getTitle());
        }
        return course;
    }
}
