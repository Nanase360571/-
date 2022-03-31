package com.wyu.tea.readExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName:com.wyu.tea.readExcel
 * @ClassName:DataDemoListener
 * @Description:
 * @author:Aan
 * @data 2022/1/24 22:54
 **/
public class DataDemoListener extends AnalysisEventListener<DataDemo> {

    private static final int BATCH_COUNT = 5;
    List<DataDemo> list=new ArrayList<>();

    @Override
    public void invoke(DataDemo dataDemo, AnalysisContext analysisContext) {
        System.out.println(dataDemo.getAccount());
        System.out.println(dataDemo.getClassNo());
        System.out.println(dataDemo.getName());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("end================");
    }
}
