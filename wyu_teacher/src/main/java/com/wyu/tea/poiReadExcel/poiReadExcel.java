package com.wyu.tea.poiReadExcel;

import com.wyu.common.dao.pojo.db;
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

import java.io.*;
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

    public static List<db> readDb(Integer courseId,String fileName){
List<db> list = new ArrayList<>();
try {
    InputStream inputStream = new FileInputStream(fileName);
    HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
    HSSFSheet sheet = workbook.getSheetAt(0);
    int lastRowNum = sheet.getLastRowNum();
    for(int i = 1;i<=lastRowNum;i++){
        HSSFRow row = sheet.getRow(i);
        db db = new db();
        db.setDbCourse(courseId);
        HSSFCell cell1 = row.getCell(0);
        String contentAndOption = cell1.getStringCellValue();
        db.setDbContent(contentAndOption);
        HSSFCell cell2 = row.getCell(1);
        String answer = cell2.getStringCellValue();
        db.setDbAnswer(answer);
        HSSFCell cell3 = row.getCell(2);

        String dbType = cell3.getStringCellValue();
        if("单选题".equals(dbType)){
            db.setDbType(1);
            String answerContent = getAnswerContent(contentAndOption, answer);
            db.setDbAnswerText(answerContent);
        }
        else if("多选题".equals(dbType)){
            db.setDbType(2);
            String answerContent = getAnswerContent(contentAndOption, answer);
            db.setDbAnswerText(answerContent);
        }
        else if("判断题".equals(dbType)){
            db.setDbType(3);
            db.setDbAnswerText(cell2.getStringCellValue());
        }
        list.add(db);
    }

} catch (Exception e) {
    e.printStackTrace();
    System.out.println("读取题目失败");
}
return list;
    }

    public static String getAnswerContent(String contentAndOption,String answer){
        String[] split = answer.split("");
        /*
        * 处理单选题
        * */
        if(split.length == 1){
            if("A".equals(split[0])){
                String answer1 = getAnswer(contentAndOption, 0);
                return getAnswer(contentAndOption, 0);
            }else if("B".equals(split[0])){
                return getAnswer(contentAndOption, 1);
            }else if("C".equals(split[0])){
                return getAnswer(contentAndOption, 2);
            }else if("D".equals(split[0])){
                return getAnswer(contentAndOption, 3);
            }
        }
        /*
        * 处理多选题
        * */
        else {
            StringBuilder sb = new StringBuilder();

            for (String s : split) {
                if("A".equals(s)){
                    sb.append(getAnswer(contentAndOption,0));
                }else if("B".equals(s)){
                    sb.append(getAnswer(contentAndOption,1));
                }else if("C".equals(s)){
                    sb.append(getAnswer(contentAndOption,2));
                }else if("D".equals(s)){
                    sb.append(getAnswer(contentAndOption,3));
                }
                sb.append("|");
            }
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        }
        return null;
    }
    public static String getAnswer(String answerText,Integer number){
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String[] split = answerText.split("");
        for (String s : split) {
            if("|".equals(s)){
                list.add(sb.toString());
                sb= new StringBuilder();
                continue;
            }
            sb.append(s);
        }
        list.add(sb.toString());


        return list.get(number+1);
    }
}
