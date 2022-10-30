package com.atguigu.excel;

import com.alibaba.excel.EasyExcel;

public class TestRead {
    public static void main(String[] args) {
        //设置文件和路径
        String fileName = "E:\\atguigu.xlsx";
        //调用方法进行读操作
        EasyExcel.read(fileName, User.class, new ExcelListener()).sheet().doRead();
    }
}
