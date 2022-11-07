package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vod.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Ly
 * @since 2022-10-26
 */
@RestController
@RequestMapping(value = "/admin/vod/chapter")
//@CrossOrigin
@Api(tags = "课程章节")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    //1 大纲列表（章节和小节列表）
    @ApiOperation("大纲列表")
    @GetMapping("getNestedTreeList/{courseId}")
    public Result getTreeList(@PathVariable Long courseId) {
        List<ChapterVo> list = chapterService.getTreeList(courseId);
        return Result.success(list);
    }
    //添加章节
    @ApiOperation("添加课程章节")
    @PostMapping("save")
    public Result save(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return Result.success(null);
    }

    //修改 根据id查询
    @ApiOperation("根据Id查询课程章节")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Chapter chapter = chapterService.getById(id);
        return Result.success(chapter);
    }

    //修改最终实现
    @ApiOperation("修改课程章节")
    @PostMapping("update")
    public Result update(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.success(null);
    }


    //删除章节
    @ApiOperation("删除课程章节")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        chapterService.removeById(id);
        return Result.success(null);
    }
}

