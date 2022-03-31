package com.wyu.tea.readExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @PackageName:com.wyu.tea.readExcel
 * @ClassName:DataDemo
 * @Description:
 * @author:Aan
 * @data 2022/1/24 22:49
 **/
@Data
public class DataDemo {
    @ExcelProperty(index = 0)
    private int account;
    @ExcelProperty(index = 1)
    private String name;
    @ExcelProperty(index = 2)
    private String classNo;
}
