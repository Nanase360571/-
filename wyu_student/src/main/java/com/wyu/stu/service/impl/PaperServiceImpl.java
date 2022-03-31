package com.wyu.stu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.common.dao.pojo.db;
import com.wyu.common.dao.pojo.paper;
import com.wyu.common.dao.pojo.paperStudentDb;
import com.wyu.common.util.DealContent;
import com.wyu.common.vo.Result;
import com.wyu.common.vo.dbVo;
import com.wyu.common.vo.dbVoList;
import com.wyu.stu.dao.mapper.PaperStudentDbMapper;
import com.wyu.stu.dao.mapper.PaperStudentMapper;
import com.wyu.stu.dao.pojo.paperStudent;
import com.wyu.stu.dao.teacherMapper.DbMapper;
import com.wyu.stu.dao.teacherMapper.PaperMapper;
import com.wyu.stu.service.PaperService;
import com.wyu.stu.vo.paperVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
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

    @Override
    public Result getPaperDb(Integer studentId, Integer paperId) {
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
        Collections.shuffle(multipleChoiceArrayList);
        Collections.shuffle(singleChoiceArrayList);
        Collections.shuffle(tfArrayList);
        for (Object o : multipleChoiceArrayList) {
            dbVo dbVo = (dbVo)o;
            ArrayList<String> option = dbVo.getOption();
            Collections.shuffle(option);
            dbVo.setOption(option);
            dbVo.setAnswer(null);
            o = dbVo;
        }
        for (Object o : singleChoiceArrayList) {
            dbVo dbVo = (dbVo)o;
            ArrayList<String> option = dbVo.getOption();
            Collections.shuffle(option);
            dbVo.setOption(option);
            dbVo.setAnswer(null);
            o = dbVo;

        }
        for (Object o : tfArrayList) {
            dbVo dbVo = (dbVo)o;
            ArrayList<String> option = dbVo.getOption();
            dbVo.setOption(option);
            dbVo.setAnswer(null);
            o = dbVo;

        }
        content.setSingleChoiceArrayList(singleChoiceArrayList);
        content.setTfArrayList(tfArrayList);
        content.setMultipleChoiceArrayList(multipleChoiceArrayList);

        /*
        * 获取开考时间 结束时间 各个模块分数 每道题的分数等信息
        * */
        paper paper = paperMapper.selectById(paperId);
        Integer singlePoint = paper.getPapTotal()/paper.getPapSingle();
        Integer multiPoint = paper.getPapTotal()/paper.getPapMulti();
        Integer judgePoint = paper.getPapTotal()/paper.getPapJudge();
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = new Date();
        Date endTime = new Date();
        String hms ="";
        try{
            startTime = fmt.parse(paper.getPapStart());
            endTime = fmt.parse(paper.getPapEnd());
            //先将两个时间转换为毫秒相减，得到相差的毫秒数
            long number = endTime.getTime() - startTime.getTime();
            //这里想要只保留分秒可以写成"mm:ss"
            SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
            //这里很重要，如果不设置时区的话，输出结果就会是几点钟，而不是毫秒值对应的时分秒数量了。
            formatter2.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
            hms = formatter2.format(number);

        }catch (Exception e){
            System.out.println(e.getClass()+"日期转换有问题");
        }
        paperVo.setDbVoList(content);
        paperVo.setSinglePoint(singlePoint);
        paperVo.setMultiPoint(multiPoint);
        paperVo.setJudgePoint(judgePoint);
        paperVo.setStartTime(paper.getPapStart());
        paperVo.setEndTime(paper.getPapEnd());
        paperVo.setFillTime(hms);
        return Result.success(paperVo);

    }
}
