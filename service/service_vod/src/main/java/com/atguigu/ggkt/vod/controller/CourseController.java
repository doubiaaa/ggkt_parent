package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.service.CourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Ly
 * @since 2022-10-26
 */
@RestController
@RequestMapping(value = "/admin/vod/course")
@CrossOrigin
@Api(tags = "课程管理接口")
public class CourseController {
    @Autowired
    private CourseService courseService;

    //添加课程基本信息
    @ApiOperation("添加课程基本信息")
    @PostMapping("save")
    public Result save(@RequestBody CourseFormVo courseFormVo) {
        Long CourseID = courseService.saveCourseInfo(courseFormVo);

        return Result.success(CourseID);
    }


    //点播课程列表
    @ApiOperation("点播课程列表")
    @GetMapping("{page}/{limit}")
    public Result CourseList(@PathVariable Long page,
                             @PathVariable Long limit,
                             CourseQueryVo courseQueryVo
    ) {
        Page<Course> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.findPageCourse(pageParam, courseQueryVo);
        return Result.success(map);
    }

    //根据id获取课程信息
    @ApiOperation("根据id获取课程信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        CourseFormVo courseFormVo = courseService.getCourseInfoById(id);
        return Result.success(courseFormVo);
    }

    //修改课程信息
    @ApiOperation("修改课程信息")
    @PostMapping("update")
    public Result get(@RequestBody CourseFormVo courseFormVo) {
        courseService.updateCourseId(courseFormVo);
        return Result.success(courseFormVo.getId());

    }

    //根据课程Id查询发布课程信息
    @ApiOperation("Id查询发布课程信息")
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVo(@PathVariable Long id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return Result.success(coursePublishVo);
    }

    //课程最终发布
    @ApiOperation("课程最终发布")
    @PutMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable Long id) {
        courseService.publishCourse(id);
        return Result.success(null);

    }

    //删除课程
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        courseService.removeCourseId(id);
        return Result.success(null);
    }
}

