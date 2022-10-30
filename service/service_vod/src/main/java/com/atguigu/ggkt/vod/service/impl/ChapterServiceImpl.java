package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vo.vod.VideoVo;
import com.atguigu.ggkt.vod.mapper.ChapterMapper;
import com.atguigu.ggkt.vod.service.ChapterService;
import com.atguigu.ggkt.vod.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Ly
 * @since 2022-10-26
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;


    //大纲列表(章节和小节列表)
    @Override
    public List<ChapterVo> getTreeList(Long courseId) {
        //定义最终数据List集合
        List<ChapterVo> finalChapterList = new ArrayList<>();
        //创建条件构造器
        LambdaQueryWrapper<Chapter> queryWrapper = new LambdaQueryWrapper<>();
        //根据courseId获取课程中所有章节
        queryWrapper.eq(Chapter::getCourseId, courseId);
        List<Chapter> Chapterlist = baseMapper.selectList(queryWrapper);
        //创建条件构造器
        LambdaQueryWrapper<Video> videoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //根据courseId获取课程中所有小节
        videoLambdaQueryWrapper.eq(Video::getCourseId, courseId);
        List<Video> VideoList = videoService.list(videoLambdaQueryWrapper);
        //遍历章节
        //封装所有章节
        for (Chapter chapter : Chapterlist) {
            ChapterVo chapterVo = new ChapterVo();
            //得到每个章节对象放到finalChapterList中去
            BeanUtils.copyProperties(chapter, chapterVo);
            finalChapterList.add(chapterVo);

            //封装章节里面小节
            List<VideoVo> videoVoList = new ArrayList<>();
            //遍历小节list
            for (Video video : VideoList) {
                //判断小节在那个章节下面
                //章节id 和 小节chapter_id
                if (chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);

                }
            }
            //把章节里面所有小节集合到整个章节当中
            chapterVo.setChildren(videoVoList);
        }
        return finalChapterList;
    }
}
