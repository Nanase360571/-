package com.wyu.tea.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.junit.Test;

import java.awt.*;
import java.io.File;

/**
 * @PackageName:com.wyu.tea.test
 * @ClassName:test
 * @Description:
 * @author:Aan
 * @data 2022/1/19 13:52
 **/
public class test {
    public static void main(String[] args) {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String fileName = "C:"+ File.separator+"Users" + File.separator +"86158" + File.separator + "Desktop" + File.separator +"demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();

        // 写法2：
        ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }
    @Test
    public void indexOrNameRead() {
        String fileName = "C:"+ File.separator+"Users" + File.separator +"86158" + File.separator + "Desktop" + File.separator +"demo.xlsx";
        // 这里默认读取第一个sheet
        EasyExcel.read(fileName, IndexOrNameData.class, new IndexOrNameDataListener()).sheet().doRead();
    }
}
