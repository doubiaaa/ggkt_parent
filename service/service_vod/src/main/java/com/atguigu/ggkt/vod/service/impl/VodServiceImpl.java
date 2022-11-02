package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.vod.service.VodService;
import com.atguigu.ggkt.vod.utils.ConstantPropertiesUtil;
import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaResponse;
import org.springframework.stereotype.Service;

/**
 * @Projectname: ggkt_parent
 * @Filename: VodServiceImpl
 * @Author: Ly
 * @Data:2022/11/1 21:12
 */
@Service
public class VodServiceImpl implements VodService {

    //删除腾讯云视频
    @Override
    public void removeVideo(String fileId) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("vod.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            VodClient client = new VodClient(cred, "", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DeleteMediaRequest req = new DeleteMediaRequest();
            req.setFileId(fileId);
            // 返回的resp是一个DeleteMediaResponse的实例，与请求对象对应
            DeleteMediaResponse resp = client.DeleteMedia(req);
            // 输出json格式的字符串回包
            System.out.println(DeleteMediaResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            throw new GgktException(20001, "删除视频失败");
        }
    }

    //上传视频接口
    @Override
    public String uploadVideo() {

        //指定腾讯云账号ID和Key
        VodUploadClient client = new VodUploadClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                ConstantPropertiesUtil.ACCESS_KEY_SECRET);

        //上传请求对象
        VodUploadRequest request = new VodUploadRequest();
        //设置上传视频的本地路径
        request.setMediaFilePath("E:\\BaiduNetdiskDownload\\1、Linux零基础快速入门到精通\\Linux视频教程\\第1章\\第一章-01-操作系统概述.mp4");
        request.setProcedure("LongVideoPreset");
        try {
            //调用方法上传视频 指定地域
            VodUploadResponse response = client.upload("ap-guangzhou", request);
            //获取上传视频Id
            String fileId = response.getFileId();
            return fileId;
        } catch (Exception e) {
            // 业务方进行异常处理
            throw new GgktException(20001, "上传视频失败");
        }

    }
}
