package com.wyu.tea.readExcel;

import com.alibaba.excel.EasyExcel;
import com.wyu.tea.test.IndexOrNameData;
import com.wyu.tea.test.IndexOrNameDataListener;
import org.junit.Test;

import java.io.File;

/**
 * @PackageName:com.wyu.tea.readExcel
 * @ClassName:ExcelReadTest
 * @Description:
 * @author:Aan
 * @data 2022/1/24 22:57
 **/
public class ExcelReadTest {
    @Test
    public void test1(){
        //C:\Users\86158\Desktop\教师点名册.xls
        String fileName = "C:"+ File.separator+"Users" + File.separator +"86158" + File.separator + "Desktop" + File.separator +"教师点名册.xls";
        // 这里默认读取第一个sheet
        EasyExcel.read(fileName, DataDemo.class, new DataDemoListener()).headRowNumber(4).sheet().doRead();
    }
}
