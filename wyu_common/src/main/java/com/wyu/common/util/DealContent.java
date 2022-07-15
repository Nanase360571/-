package com.wyu.common.util;

import com.wyu.common.dao.pojo.db;
import com.wyu.common.vo.dbVo;
import com.wyu.common.vo.dbVoList;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @PackageName:com.wyu.common.util
 * @ClassName:DealContent
 * @Description:
 * @author:Aan
 * @data 2022/1/18 23:18
 * 从题库中将题目查询出来并封装好
 **/

public class DealContent {
    public static dbVoList getContent(List list){
        ArrayList<dbVo> singleChoiceArrayList = new ArrayList<>();
        for (Object object : list) {
            db db=(db)object;
            int dbType = db.getDbType();
            if(dbType == 1){
                dbVo singleChoice = getSingleChoice(db);
                singleChoiceArrayList.add(singleChoice);
            }
        }
        ArrayList<dbVo> multipleChoiceArrayList = new ArrayList<>();
        for (Object object : list) {
            db db=(db)object;
            int dbType = db.getDbType();
            if(dbType == 2){
                dbVo multipleChoice = getMultipleChoice(db);
                multipleChoiceArrayList.add(multipleChoice);
            }
        }
        ArrayList<dbVo> tfArrayList = new ArrayList<>();

        for (Object object : list) {
            db db=(db)object;
            int dbType = db.getDbType();
            if(dbType == 3){
                dbVo tf = getTF(db);
                tfArrayList.add(tf);
            }
        }
        dbVoList dbVoList = new dbVoList();
        dbVoList.setTfArrayList(tfArrayList);
        dbVoList.setMultipleChoiceArrayList(multipleChoiceArrayList);
        dbVoList.setSingleChoiceArrayList(singleChoiceArrayList);
        return dbVoList;
    }

    private static dbVo getTF(db db) {
        dbVo dbVo=new dbVo();
        dbVo.setTitle(db.getDbContent());
        ArrayList<String> answer = new ArrayList();
        answer.add(db.getDbAnswer());
        dbVo.setAnswer(answer);
        dbVo.setId(db.getId());
        dbVo.setDbType(db.getDbType());
        return dbVo;
    }
    private static dbVo getMultipleChoice(db db) {
        String dbContent = db.getDbContent();
        String[] split = dbContent.split("");
        ArrayList<String> option = new ArrayList<>();
        StringBuffer buffer=new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            String c = split[i];
            if(c.equals("|")){
                option.add(buffer.toString());
                buffer=new StringBuffer();
                continue;
            }
            buffer.append(c);

        }
        option.add(buffer.toString());
        dbVo dbVo=new dbVo();
        BeanUtils.copyProperties(db,dbVo);
        // 第一次遍历出来的option包含了title，将其复制后remove，再将option赋值
        dbVo.setTitle(option.get(0));
        option.remove(0);
        dbVo.setOption(option);
        ArrayList<String> answer = new ArrayList();
        String dbAnswer = db.getDbAnswer();
        String[] split1 = dbAnswer.split("");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < split1.length; i++) {
            stringBuffer.append(split1[i]);
            answer.add(stringBuffer.toString());
            stringBuffer = new StringBuffer();
        }
        dbVo.setAnswer(answer);
        return dbVo;
    }

    public static dbVo getSingleChoice(db db){
        String dbContent = db.getDbContent();
        String[] split = dbContent.split("");
        ArrayList<String> option = new ArrayList<>();
        StringBuffer buffer=new StringBuffer();
        String c = null;
        for (int i = 0; i < split.length; i++) {
             c = split[i];
            if(c.equals("|")){
                option.add(buffer.toString());
                buffer=new StringBuffer();
                continue;
            }
            buffer.append(c);

        }
        option.add(buffer.toString());

        dbVo dbVo=new dbVo();
        BeanUtils.copyProperties(db,dbVo);
        // 第一次遍历出来的option包含了title，将其复制后remove，再将option赋值
        dbVo.setTitle(option.get(0));
        option.remove(0);
        dbVo.setOption(option);
        ArrayList<String> answer = new ArrayList();
        answer.add(db.getDbAnswer());
        dbVo.setAnswer(answer);
        return dbVo;
    }

    public static ArrayList<String> getMultipleAnswer(db db){
        String dbContent = db.getDbAnswerText();
        String[] split = dbContent.split("");
        ArrayList<String> answerList = new ArrayList<>();
        StringBuffer buffer=new StringBuffer();
        String c = null;
        for (int i = 0; i < split.length; i++) {
            c = split[i];
            if(c.equals("|")){
                answerList.add(buffer.toString());
                buffer=new StringBuffer();
                continue;
            }
            buffer.append(c);

        }
        answerList.add(buffer.toString());

        return answerList;
    }
}
