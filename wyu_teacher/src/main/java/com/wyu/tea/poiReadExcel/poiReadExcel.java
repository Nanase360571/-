package com.wyu.tea.poiReadExcel;

import com.wyu.tea.dao.pojo.student;
import com.wyu.tea.vo.params.StudentFromExcel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName:com.wyu.tea.poiReadExcel
 * @ClassName:Demo1
 * @Description:
 * @author:Aan
 * @data 2022/1/24 23:58
 **/
public class poiReadExcel {

    public static List<student> read(String fileName){
//        String fileName = "C:"+ File.separator+"Users" + File.separator +"86158" + File.separator + "Desktop" + File.separator +"教师点名册.xls";
        List<student> list = new ArrayList<>();
        try {
            InputStream inputStream=new FileInputStream(fileName);
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

            HSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for(int i = 5; i<=lastRowNum; i++){
                StudentFromExcel studentFromExcel = new StudentFromExcel();
                student student = new student();
                HSSFRow row = sheet.getRow(i);
                int j = 1;
                HSSFCell cell1 = row.getCell(0);
                studentFromExcel.setAccount(cell1.getStringCellValue());
                HSSFCell cell2 = row.getCell(1);
                studentFromExcel.setName(cell2.getStringCellValue());
                HSSFCell cell3 = row.getCell(3);
                studentFromExcel.setClaNo(cell3.getStringCellValue());
                BeanUtils.copyProperties(studentFromExcel,student);

                list.add(student);
            }
            for (student student : list) {
                System.out.println(student.getAccount()+":"+student.getName());

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
