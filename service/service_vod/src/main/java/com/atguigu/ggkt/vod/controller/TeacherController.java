package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Ly
 * @since 2022-10-05
 */
@Api(tags = "讲师管理接口")
@RestController
//@CrossOrigin //跨域
@RequestMapping(value = "/admin/vod/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    //查询所有讲师
    @ApiOperation("查询所有讲师")
    @GetMapping("findAll")
    public Result findAllTeacher() {
        /**
         * 自定义异常处理
         */
        //try {
        //    int a = 10 / 0;
        //} catch (Exception e) {
        //    //抛出异常
        //    throw new GgktException(201, "执行自定义GgktException异常处理");
        //}

        List<Teacher> list = teacherService.list();
        return Result.success(list).message("消息返回成功");
    }

    //逻辑删除讲师
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public Result removeTeacher(@ApiParam(name = "id", value = "ID", required = true)
                                @PathVariable Long id) {
        boolean isSuccess = teacherService.removeById(id);
        if (isSuccess) {
            return Result.success(null);
        } else {
            return Result.fail(null);
        }
    }

    //条件查询分页
    @ApiOperation("条件查询分页")
    @PostMapping("findQueryPage/{current}/{limit}")
    public Result findPage(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        //创建分页对象
        Page<Teacher> pageParam = new Page<>(current, limit);
        if (teacherQueryVo == null) {
            IPage<Teacher> page = teacherService.page(pageParam, null);
            return Result.success(page);
        } else {
            //获取条件值
            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();
            //创建条件构造器
            LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
            //进行非空判断 条件封装
            if (!StringUtils.isEmpty(name)) {
                wrapper.like(Teacher::getName, name);
            }
            if (!StringUtils.isEmpty(level)) {
                wrapper.like(Teacher::getLevel, level);
            }
            if (!StringUtils.isEmpty(joinDateBegin)) {
                wrapper.ge(Teacher::getJoinDate, joinDateBegin);
            }
            if (!StringUtils.isEmpty(joinDateEnd)) {
                wrapper.le(Teacher::getJoinDate, joinDateEnd);
            }
            wrapper.orderByAsc(Teacher::getJoinDate);
            IPage<Teacher> page = teacherService.page(pageParam, wrapper);

            return Result.success(page);
        }

    }

    //添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("saveTeacher")
    public Result seveTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.save(teacher);
        if (isSuccess) {
            return Result.success(null).message("添加成功");
        } else {
            return Result.fail(null);
        }
    }

    //查询讲师信息
    @ApiOperation("根据Id查询")
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.success(teacher);
    }

    //修改讲师信息
    @ApiOperation("修改讲师信息")
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.updateById(teacher);
        if (isSuccess) {
            return Result.success(null);
        } else {
            return Result.fail(null);
        }
    }

    @ApiOperation("批量删除讲师")
    @DeleteMapping("removeBarch")
    public Result removeBarch(@RequestBody List<Long> idList) {
        boolean isSuccess = teacherService.removeByIds(idList);
        if (isSuccess) {
            return Result.success(null).message("删除成功");
        } else {
            return Result.fail(null).message("删除失败");
        }

    }
}

