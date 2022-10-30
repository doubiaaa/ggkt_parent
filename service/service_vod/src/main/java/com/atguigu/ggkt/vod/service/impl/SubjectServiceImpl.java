package com.atguigu.ggkt.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.atguigu.ggkt.vod.listener.SubujectListener;
import com.atguigu.ggkt.vod.mapper.SubjectMapper;
import com.atguigu.ggkt.vod.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Ly
 * @since 2022-10-24
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubujectListener subujectListener;

    //课程分类列表
    //懒加载 每次查询一层数据
    @Override
    public List<Subject> selectChildSubject(Long id) {
        //创建构造方法
        LambdaQueryWrapper<Subject> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Subject::getParentId, id);
        List<Subject> subjectList = baseMapper.selectList(queryWrapper);
        //遍历集合 得到对象 判断是否有下一层数据
        for (Subject subject : subjectList) {
            //获取id
            Long subjectId = subject.getId();
            //查询数据
            boolean isChild = this.isChildren(subjectId);
            //封装对象
            subject.setHasChildren(isChild);
        }

        return subjectList;
    }

    //课程分类导出
    @Override
    public void exportData(HttpServletResponse response) {

        try {
            //设置下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = null;
            fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            List<Subject> list = baseMapper.selectList(null);

            List<SubjectEeVo> subjectEeVoList = new ArrayList<>();

            for (Subject subject : list) {
                SubjectEeVo subjectEeVo = new SubjectEeVo();
                BeanUtils.copyProperties(subject, subjectEeVo);
                subjectEeVoList.add(subjectEeVo);
            }

            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class).sheet("课程分类").doWrite(subjectEeVoList);

        } catch (Exception e) {
            throw new GgktException(20001, "导出失败");
        }
    }

    //课程分类导入
    @Override
    public void importData(MultipartFile file) {

        try {
            EasyExcel.read(file.getInputStream(), SubjectEeVo.class,
                    subujectListener).sheet().doRead();
        } catch (IOException e) {
            throw new GgktException(20001, "导入失败");
        }



    }


    //判断是否有下一层数据
    private boolean isChildren(Long subjectId) {
        //创建构造方法
        LambdaQueryWrapper<Subject> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Subject::getParentId, subjectId);
        //查询条数
        Integer Count = baseMapper.selectCount(queryWrapper);
        return Count > 0;
    }
}
