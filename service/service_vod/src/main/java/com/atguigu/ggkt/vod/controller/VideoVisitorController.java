package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vod.service.VideoVisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 前端控制器
 * </p>
 *
 * @author Ly
 * @since 2022-11-01
 */
@Api(tags = "课程管理统计")
@RestController
@RequestMapping(value = "/admin/vod/videoVisitor")
//@CrossOrigin
public class VideoVisitorController {

    @Autowired
    private VideoVisitorService videoVisitorService;


    //课程统计查询
    @ApiOperation("课程统计查询")
    @GetMapping("findCount/{courseId}/{startDate}/{endDate}")
    public Result findCount(@PathVariable Long courseId,
                            @PathVariable String startDate,
                            @PathVariable String endDate) {
        Map<String, Object> map = videoVisitorService.findCount(courseId, startDate, endDate);
        return Result.success(map);


    }
}

