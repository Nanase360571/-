package com.wyu.stu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wyu.common.dao.pojo.db;
import com.wyu.common.dao.pojo.paper;
import com.wyu.common.dao.pojo.paperStudentDb;
import com.wyu.common.dao.pojo.studentPaper;
import com.wyu.common.util.DealContent;
import com.wyu.common.vo.Result;
import com.wyu.common.vo.dbVo;
import com.wyu.common.vo.dbVoList;
import com.wyu.stu.dao.mapper.PaperStudentDbMapper;
import com.wyu.stu.dao.mapper.PaperStudentMapper;
import com.wyu.stu.dao.mapper.StudentPaperMapper;
import com.wyu.stu.dao.pojo.paperStudent;
import com.wyu.stu.dao.pojo.teacher;
import com.wyu.stu.dao.teacherMapper.DbMapper;
import com.wyu.stu.dao.teacherMapper.PaperMapper;
import com.wyu.stu.service.PaperService;
import com.wyu.stu.util.ConstValue;
import com.wyu.stu.vo.paperVo;
import com.wyu.stu.vo.params.SubmitPaperParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @PackageName:com.wyu.stu.service.impl
 * @ClassName:PaperServiceImpl
 * @Description:
 * @author:Aan
 * @data 2022/1/21 23:32
 **/
@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperStudentDbMapper paperStudentDbMapper;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private DbMapper dbMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StudentPaperMapper studentPaperMapper;


    @Override
    public Result getPaperSummary(Integer id) {
        QueryWrapper<paperStudentDb> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("psd_student",id);
        List<paperStudentDb> paperStudentDbs = paperStudentDbMapper.selectList(queryWrapper);
        if(paperStudentDbs.size() == 0)
        {
            return Result.fail(10001,"没有试卷");
        }
        List<Integer> collect = paperStudentDbs.stream().map(paperStudentDb::getPsdPaper).collect(Collectors.toList());
        List<paper> papers = paperMapper.selectBatchIds(collect);
        return Result.success(papers);


    }

    /*
    * 获取试卷，将试卷存入redis
    * */
    @Override
    public Result getPaperDb(Integer studentId, Integer paperId) {
        QueryWrapper<studentPaper> studentPaperQueryWrapper = new QueryWrapper<>();
        studentPaperQueryWrapper.eq("sp_student",studentId)
                .eq("sp_paper",paperId);
        studentPaper studentPaper = studentPaperMapper.selectOne(studentPaperQueryWrapper);
        if(studentPaper.getStatus() == 1){
            return Result.fail(100001,"您已参加了这门考试了");
        }

        Object o1 = redisTemplate.opsForValue().get("PAPERID" + paperId);
        if(o1 != null){
            paperVo paperVo = JSON.parseObject((String) o1, paperVo.class);
            dbVoList dbVoList = paperVo.getDbVoList();
            ArrayList singleChoiceArrayList = dbVoList.getSingleChoiceArrayList();
            ArrayList multipleChoiceArrayList = dbVoList.getMultipleChoiceArrayList();
            ArrayList tfArrayList = dbVoList.getTfArrayList();
            ArrayList single = new ArrayList();
            ArrayList multiple = new ArrayList();
            ArrayList tf = new ArrayList();

            for (Object o : singleChoiceArrayList) {
                o =JSONObject.toJavaObject((JSON) o,dbVo.class);
                single.add(o);
            }for (Object o : multipleChoiceArrayList) {
                o =JSONObject.toJavaObject((JSON) o,dbVo.class);
                multiple.add(o);
            }for (Object o : tfArrayList) {
                o =JSONObject.toJavaObject((JSON) o,dbVo.class);
                tf.add(o);
            }
            dbVoList.setSingleChoiceArrayList(ex(single));
            dbVoList.setMultipleChoiceArrayList(ex(multiple));
            Collections.shuffle(tf);
            dbVoList.setSingleChoiceArrayList(single);
            dbVoList.setMultipleChoiceArrayList(multiple);
            dbVoList.setTfArrayList(tf);
            paperVo.setDbVoList(dbVoList);
            return Result.success(paperVo);
        }


        paperVo paperVo = new paperVo();
        QueryWrapper<paperStudentDb> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("psd_student",studentId);
        queryWrapper.eq("psd_paper",paperId);
        List<paperStudentDb> paperStudentDbs = paperStudentDbMapper.selectList(queryWrapper);
        if(paperStudentDbs.size() == 0)
        {
            return Result.fail(10001,"没有题目");
        }
        List<Integer> collect = paperStudentDbs.stream().map(paperStudentDb::getPsdDb).collect(Collectors.toList());
        List<db> dbs = dbMapper.selectBatchIds(collect);
        dbVoList content = DealContent.getContent(dbs);
        ArrayList multipleChoiceArrayList = content.getMultipleChoiceArrayList();
        ArrayList singleChoiceArrayList = content.getSingleChoiceArrayList();
        ArrayList tfArrayList = content.getTfArrayList();
        Collections.shuffle(tfArrayList);
        for (Object o : tfArrayList) {
            dbVo dbVo = (dbVo)o;
            ArrayList<String> option = dbVo.getOption();
            dbVo.setOption(option);
            dbVo.setAnswer(null);
            o = dbVo;
        }
        content.setSingleChoiceArrayList(ex(singleChoiceArrayList));
        content.setMultipleChoiceArrayList(ex(multipleChoiceArrayList));
        content.setTfArrayList(tfArrayList);
        /*
        * 获取开考时间 结束时间 各个模块分数 每道题的分数等信息
        * */
        paper paper = paperMapper.selectById(paperId);
        Integer singlePoint = paper.getPapTotal()/paper.getPapSingle();
        Integer multiPoint = paper.getPapTotal()/paper.getPapMulti();
        Integer judgePoint = paper.getPapTotal()/paper.getPapJudge();
        paperVo.setDbVoList(content);
        paperVo.setSinglePoint(singlePoint);
        paperVo.setMultiPoint(multiPoint);
        paperVo.setJudgePoint(judgePoint);
        paperVo.setStartTime(paper.getPapStart());
        paperVo.setEndTime(paper.getPapEnd());
        String papExamTime = paper.getPapExamTime();
        Double aDouble = Double.valueOf(papExamTime);
        Double hours = (aDouble*60)%60;
        Double minutes = (aDouble*60) - hours*60;
        paperVo.setHours(hours.intValue());
        paperVo.setMinutes(minutes.intValue());
        redisTemplate.opsForValue().set("PAPERID"+paperId, JSON.toJSONString(paperVo),1, TimeUnit.DAYS);

        return Result.success(paperVo);

    }

    /*
     *   处理学生传来的答案
     * 1.从paper种获取每道题的的分值
     * 2.从db库中查询出所有题目的答案
     * 3.将上述的查询结果记录，并存入到redis中，方便其他学生提交同一份试卷的时候，反复查询数据库
     *
     * */
    @Override
    @Transactional
    public Result submitPaper(SubmitPaperParams submitPaperParams) {

        paper paper = paperMapper.selectById(submitPaperParams.getPaperId());
        /**
         * 获取各个模块的题目量
         * */
        Integer papSingle = paper.getPapSingle();
        Integer papMulti = paper.getPapMulti();
        Integer papJudge = paper.getPapJudge();
        /**
         * 获取各个模块的分数
         * */
        Integer papSingleSum = paper.getPapSingleSum();
        Integer papMultiSum = paper.getPapMultiSum();
        Integer papJudgeSum = paper.getPapJudgeSum();
        Double singlePoint = (double)papSingleSum/(double)papSingle ;
        Double multiPoint = (double)papMultiSum/(double)papMulti;
        Double judgePoint = (double)papJudgeSum/(double)papJudge;

        ArrayList<dbVo> singleChoiceArrayList = submitPaperParams.getSingleChoiceArrayList();
        ArrayList<dbVo> multipleChoiceArrayList = submitPaperParams.getMultipleChoiceArrayList();
        ArrayList<dbVo> tfArrayList = submitPaperParams.getTfArrayList();
        List<Integer> collect1 = singleChoiceArrayList.stream().map(dbVo::getId).collect(Collectors.toList());
        List<Integer> collect2 = multipleChoiceArrayList.stream().map(dbVo::getId).collect(Collectors.toList());
        List<Integer> collect3= tfArrayList.stream().map(dbVo::getId).collect(Collectors.toList());
        List<Integer> allCollect = new ArrayList<>();
        allCollect.addAll(collect1);
        allCollect.addAll(collect2);
        allCollect.addAll(collect3);
        /*
         * 分别查询单选多选判断题目的答案
         *
         * */
        List<db> dbs = dbMapper.selectBatchIds(allCollect);
        List<db> dbSingle = new ArrayList<>();
        List<db> dbMultiple = new ArrayList<>();
        List<db> dbTf = new ArrayList<>();
        Map<Integer,ArrayList<String>>  dbSingleAnswerMap = new HashMap<>();
        Map<Integer,ArrayList<String>>  dbMultipleAnswerMap = new HashMap<>();
        Map<Integer,ArrayList<String>>  dbTfAnswerMap = new HashMap<>();
        for (db db : dbs) {
            if(db.getDbType() == 1)
            {
                dbSingle.add(db);
                ArrayList<String> value = new ArrayList<>();
                value.add(db.getDbAnswerText());
                dbSingleAnswerMap.put(db.getId(), value);
            }if(db.getDbType() == 2)
            {
                dbMultiple.add(db);
                dbMultipleAnswerMap.put(db.getId(),DealContent.getMultipleAnswer(db));

            }if(db.getDbType() == 3)
            {
                dbTf.add(db);
                ArrayList<String> value = new ArrayList<>();
                value.add(db.getDbAnswerText());
                dbTfAnswerMap.put(db.getId(), value);
            }
        }
        /*
        *   对答案流程
        *   1.将查询来的答案分割为String数组
        *   2.将学生答案和查询来的答案，即两个数组，进行比对
        * */
        List<paperStudentDb> list = new ArrayList<>();
        for (dbVo dbVo : singleChoiceArrayList) {
            paperStudentDb paperStudentDb = new paperStudentDb();
            paperStudentDb.setPsdPaper(paper.getId());
            paperStudentDb.setPsdStudent(submitPaperParams.getStudentId());
            paperStudentDb.setPsdDb(dbVo.getId());
            paperStudentDb.setPsdAnswer(dbVo.getStuAnswer().get(0));
            paperStudentDb.setPsdDbType(dbVo.getDbType());
            ArrayList<String> strings = dbSingleAnswerMap.get(dbVo.getId());
            /*判断这道单选题是否答对*/
            if(strings.get(0).equals(dbVo.getStuAnswer().get(0))){
                paperStudentDb.setPsdScore(singlePoint);
            }else {
                paperStudentDb.setPsdScore(0.0);
            }
            list.add(paperStudentDb);
        }
        for (dbVo dbVo : multipleChoiceArrayList) {
            paperStudentDb paperStudentDb = new paperStudentDb();
            paperStudentDb.setPsdPaper(paper.getId());
            paperStudentDb.setPsdStudent(submitPaperParams.getStudentId());
            paperStudentDb.setPsdDb(dbVo.getId());
            paperStudentDb.setPsdDbType(dbVo.getDbType());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dbVo.getStuAnswer().size(); i++) {
                sb.append(dbVo.getStuAnswer().get(i));
                if(i==dbVo.getStuAnswer().size()-1){
                 continue;
                }
                sb.append("|");
            }
            paperStudentDb.setPsdAnswer(sb.toString());
            //获取题库的答案
            ArrayList<String> strings = dbMultipleAnswerMap.get(dbVo.getId());
            /*判断这道多选题是否答对*/
            Double tag = multiPoint;
            if(strings.size() != dbVo.getStuAnswer().size())
            {
                tag = 0.0;
            }else {
                for (String string : strings) {
                    if(!dbVo.getStuAnswer().contains(string)){
                        tag = 0.0;
                        break;
                    }
                }
            }



//            for (String s : dbVo.getStuAnswer()) {
//                if(!strings.contains(s)){
//                    tag = 0;
//                    break;
//                }
//            }
            paperStudentDb.setPsdScore(Double.valueOf(tag));

            list.add(paperStudentDb);
        }
        for (dbVo dbVo : tfArrayList) {
            paperStudentDb paperStudentDb = new paperStudentDb();
            paperStudentDb.setPsdPaper(paper.getId());
            paperStudentDb.setPsdStudent(submitPaperParams.getStudentId());
            paperStudentDb.setPsdDb(dbVo.getId());
            paperStudentDb.setPsdAnswer(dbVo.getStuAnswer().get(0));
            paperStudentDb.setPsdDbType(dbVo.getDbType());
            ArrayList<String> strings = dbTfAnswerMap.get(dbVo.getId());
            /*判断这道判断题是否答对*/
            if(strings.get(0).equals(dbVo.getStuAnswer().get(0))){
                paperStudentDb.setPsdScore(Double.valueOf(judgePoint));
            }else {
                paperStudentDb.setPsdScore(0.0);
            }
            list.add(paperStudentDb);
        }
        for (paperStudentDb paperStudentDb : list) {
            UpdateWrapper<paperStudentDb> updateWrapper = new UpdateWrapper<>();




            updateWrapper.eq("psd_student",paperStudentDb.getPsdStudent());
            updateWrapper.eq("psd_paper",paperStudentDb.getPsdPaper());
            updateWrapper.eq("psd_db",paperStudentDb.getPsdDb());
            paperStudentDbMapper.update(paperStudentDb,updateWrapper);
        }
        studentPaper studentPaper = new studentPaper();
        studentPaper.setSpPaper(submitPaperParams.getPaperId());
        studentPaper.setSpStudent(submitPaperParams.getStudentId());
        studentPaper.setStatus(1);
        QueryWrapper<studentPaper> eq = new QueryWrapper<studentPaper>()
                .eq("sp_student", studentPaper.getSpStudent())
                .eq("sp_paper", studentPaper.getSpPaper());
        int update = studentPaperMapper.update(studentPaper, eq);
        return Result.success(update);
    }

    public ArrayList ex(ArrayList list){
        Collections.shuffle(list);
        for (Object o : list) {
            dbVo dbVo = (dbVo)o;
            ArrayList<String> option = dbVo.getOption();
            Collections.shuffle(option);
            dbVo.setOption(option);
            dbVo.setAnswer(null);
        }
        return list;
    }
}
