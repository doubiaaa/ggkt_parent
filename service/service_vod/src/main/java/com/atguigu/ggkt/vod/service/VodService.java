package com.atguigu.ggkt.vod.service;

/**
 * @Projectname: ggkt_parent
 * @Filename: VodService
 * @Author: Ly
 * @Data:2022/11/1 21:12
 */
public interface VodService {

    //上传视频接口
    String uploadVideo();

    //删除腾讯云视频
    void removeVideo(String fileId);
}
