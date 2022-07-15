package com.wyu.tea.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.common.dao.pojo.*;
import com.wyu.common.vo.Result;
import com.wyu.tea.dao.mapper.*;
import com.wyu.tea.dao.pojo.student;
import com.wyu.tea.service.PaperService;
import com.wyu.tea.vo.StudentVo;
import com.wyu.tea.vo.params.*;

import org.apache.commons.collections.ArrayStack;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.JulianFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @PackageName:com.wyu.tea.service.Impl
 * @ClassName:PaperServiceImpl
 * @Description:
 * @author:Aan
 * @data 2022/3/22 10:36
 **/

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private PaperTeacherCourseMapper paperTeacherCourseMapper;
    @Autowired
    private ClassesCourseMapper classesCourseMapper;
    @Autowired
    private ClassesMapper classesMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private PaperStudentDbMapper paperStudentDbMapper;
    @Autowired
    private DbKnowledgeTeacherMapper dbKnowledgeTeacherMapper;
    @Autowired
    private PaperKnowledgeMapper paperKnowledgeMapper;
    @Autowired
    private DbMapper dbMapper;
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Autowired
    private PaperTargetMapper paperTargetMapper;
    @Autowired
    private TargetMapper targetMapper;
    @Autowired
    private DbTargetTeacherMapper dbTargetTeacherMapper;

    @Override
    public Result getAllPaperList(Integer teacherId) {
        QueryWrapper<paperTeacherCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ptc_teacher",teacherId);
        List<paperTeacherCourse> paperTeacherCourses = paperTeacherCourseMapper.selectList(queryWrapper);
        if(paperTeacherCourses.size() == 0)
        {
            return Result.fail(10003,"没有试卷");
        }
        List<Integer> collect = paperTeacherCourses.stream().map(paperTeacherCourse::getPtcPaper).collect(Collectors.toList());
        List<paper> papers = paperMapper.selectBatchIds(collect);
        return Result.success(papers);
    }

    @Override
    public Result getAllClasses(Integer teacherId, Integer paperId) {
        QueryWrapper<paperTeacherCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ptc_teacher",teacherId);
        queryWrapper.eq("ptc_paper",paperId);
        List<paperTeacherCourse> paperTeacherCourses = paperTeacherCourseMapper.selectList(queryWrapper);
        if(paperTeacherCourses.size() == 0)
        {
            return Result.fail(10003,"该课程没有班级");
        }
        List<Integer> collect = paperTeacherCourses.stream().map(paperTeacherCourse::getPtcCourse).collect(Collectors.toList());
        QueryWrapper<classesCourse> queryWrapper1 =new QueryWrapper<>();
        queryWrapper1.in("cc_course",collect);
        queryWrapper1.in("cc_teacher",teacherId);
        List<classesCourse> classesCourses = classesCourseMapper.selectList(queryWrapper1);
        List<Integer> collect1 = classesCourses.stream().map(classesCourse::getCcClass).collect(Collectors.toList());
        List<classes> classes = classesMapper.selectBatchIds(collect1);
        return Result.success(classes);
    }

    /*
    * 1.根据classId 去student表查出所有该年纪的所有学生
    * 2.studentId,paperId=>wyu_paper_student_db表获取dbId，获取来的数据包含了得分和答案值（String）,题目类型
    * 3.查询这门课的知识点,可得知题目对应对应了哪些知识点
    * 4.查询这门课的知识点占比情况
    * 5.根据paperId 查询paper 可以获取每种题型的体量 总分 可算出每个模块的每小题分数
    * 6.封装
    * */

    @Override
    public Result analyseByKnowledge(AnalyseByKnowledgeVo params) {
        String classNo = params.getClassNo();
        QueryWrapper<student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cla_no", classNo);
        List<student> studentList = studentMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(studentList)){
            return Result.fail(10001,"该年级没有学生");
        }
        List<Integer> studentIdList = studentList.stream().map(student::getId).collect(Collectors.toList());
        QueryWrapper<paperStudentDb> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("psd_paper",params.getPaperId())
                .in("psd_student",studentIdList);
        /*
        * 获取来的数据包含了得分和答案值（String）,题目类型
        * */
        List<paperStudentDb> paperStudentDbs = paperStudentDbMapper.selectList(queryWrapper1);
        if(CollectionUtils.isEmpty(studentList)){
            return Result.fail(10002,"该试卷没有题目");
        }
        List<Integer> dbIdList = paperStudentDbs.stream().map(paperStudentDb::getPsdDb).collect(Collectors.toList());
        /*
        * 根据dbList和teacherId去dbKnowledgeTeacherMapper查询knowledge
        * */
        QueryWrapper<dbKnowledgeTeacher> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.in("dkt_db",dbIdList)
                .eq("dkt_teacher",params.getTeacherId());
        List<dbKnowledgeTeacher> dbKnowledgeTeachers = dbKnowledgeTeacherMapper.selectList(queryWrapper2);
        if(CollectionUtils.isEmpty(dbKnowledgeTeachers))
        {
            return Result.fail(10003,"该课程没有知识点");
        }
        List<Integer> knowledgeIdList1 = dbKnowledgeTeachers.stream().map(dbKnowledgeTeacher::getDktKnowledge).collect(Collectors.toList());
        List<Integer> knowledgeIdList2 = knowledgeIdList1.stream().distinct().collect(Collectors.toList());
        /*
        * 查询这门课的知识点占比情况
        * */
        QueryWrapper<paperKnowledge> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("pk_paper",params.getPaperId())
                .in("pk_knowledge",knowledgeIdList2);
        List<paperKnowledge> paperKnowledgeList = paperKnowledgeMapper.selectList(queryWrapper3);
        /**
         * 根据knowledgeIdList查knowledge
         * */
        List<knowledge> knowledgeList1 = knowledgeMapper.selectBatchIds(knowledgeIdList2);

        /**
         * 查 paper
         * */

        paper paper = paperMapper.selectById(params.getPaperId());

        /**
         * 计算每种题型的每个题的分值
         * */
        int allTitleInStudent = paperStudentDbs.size();
        int studentSum = studentIdList.size();
        Double onePaperTitle = (double)allTitleInStudent/(double)studentSum;

        /**
         * 获取各个模块的题量
         * */
        Double papJudge = (double)paper.getPapJudge();
        Double papMulti = (double)paper.getPapMulti();
        Double papSingle = (double)paper.getPapSingle();
        Double papTotal = (double)paper.getPapTotal();

        /**
         * 获取各个模块总分
         * */
        Double papSingleSum = (double)paper.getPapSingleSum();
        Double papMultiSum = (double)paper.getPapMultiSum();
        Double papJudgeSum = (double)paper.getPapJudgeSum();


        Double judge = papJudgeSum/papSingle;
        Double multi = papMultiSum/papMulti;
        Double single = papSingleSum/papJudge;
        /**
         * 封装结果
         * */
        Map<String,Object> result = new HashMap<>();
        result.put("single",single);
        result.put("multi",multi);
        result.put("judge",judge);
        int k = 1 ;
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        for(Double i = 0.0;i<papSingle;i++)
        {
            list1.add(String.valueOf(k));
            k++;
        }
        k = 1;
        for(Double i = 0.0;i<papMulti;i++)
        {
            list2.add(String.valueOf(k));
            k++;
        }
        k = 1;
        for(Double i = 0.0;i<papJudge;i++)
        {
            list3.add(String.valueOf(k));
            k++;
        }

        /**
         *
         * */
        List<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("姓名");
        stringArrayList.add("学号");
        for (int i = 0; i < list1.size(); i++) {
            stringArrayList.add((i+1)+"");
        }
        stringArrayList.add("单选题模块得分");
        stringArrayList.add("单选题知识点得分");

        for (int i = 0; i < list2.size(); i++) {
            stringArrayList.add((i+1)+"");
        }
        stringArrayList.add("多选题模块得分");
        stringArrayList.add("多选题知识点得分");
        for (int i = 0; i < list3.size(); i++) {
            stringArrayList.add((i+1)+"");
        }
        stringArrayList.add("判断题模块得分");
        stringArrayList.add("判断题知识点得分");
        stringArrayList.add("试卷总得分");
        stringArrayList.add("知识点总得分");
        result.put("number",stringArrayList);
        result.put("singleNumber",list1);
        result.put("multiNumber",list2);
        result.put("judgeNumber",list3);

        result.put("papTotal",papTotal);
        /**
         * 计算每个知识点对应每个模块的一道题的分值
         * */
        Double singleEve = 0.0;
        Double multiEve = 0.0;
        Double judgeEve = 0.0;
        List<Map<String,Double>> mapList = new ArrayList<>();
        for (paperKnowledge paperKnowledge : paperKnowledgeList) {
             singleEve = (papSingle*paperKnowledge.getPkProportion()/(papSingle+papMulti+papJudge)/100)*papTotal/(papSingle*paperKnowledge.getPkProportion()/100);
             multiEve = (papMulti*paperKnowledge.getPkProportion()/(papSingle+papMulti+papJudge)/100)*papTotal/(papMulti*paperKnowledge.getPkProportion()/100);
             judgeEve = (papJudge*paperKnowledge.getPkProportion()/(papSingle+papMulti+papJudge)/100)*papTotal/(papJudge*paperKnowledge.getPkProportion()/100);
             Map<String,Double> map = new HashMap<>();
             map.put("singleEve",singleEve);
             map.put("multiEve",multiEve);
             map.put("judgeEve",judgeEve);
             map.put("knowledgeId",Double.valueOf(paperKnowledge.getPkKnowledge()));
            mapList.add(map);
        }
        List<KnowledgeAndProportionVo> knowledgeAndProportionVoList = new ArrayList<>();
        for (knowledge knowledge : knowledgeList1) {

            for (int i = 0; i < paperKnowledgeList.size(); i++) {
                if(knowledge.getId().equals(paperKnowledgeList.get(i).getPkKnowledge()) )
                {
                    KnowledgeAndProportionVo vo = new KnowledgeAndProportionVo();
                    vo.setKnowledge(knowledge);
                    vo.setProportion(paperKnowledgeList.get(i).getPkProportion());
                    for (Map<String, Double> map : mapList) {
                        if(map.get("knowledgeId").equals(Double.valueOf( knowledge.getId())) ){
                            vo.setSingleEve(map.get("singleEve"));
                            vo.setMultiEve(map.get("multiEve"));
                            vo.setJudgeEve(map.get("judgeEve"));
                        }
                    }

                    knowledgeAndProportionVoList.add(vo);
                }
            }
        }
        result.put("knowledgeAndProportionVoList",knowledgeAndProportionVoList);

        List<KnowledgePaperResultVo> list = new ArrayList<>();
        List<List> lists = new ArrayList<>();
        for (student student : studentList) {
            KnowledgePaperResultVo kpr = new KnowledgePaperResultVo();
            StudentVo studentVo = new StudentVo();
            BeanUtils.copyProperties(student,studentVo);
            //kpr.setStudent(student);
            List<DbResultVo> singleDbResultVos = new ArrayList<>();
            List<DbResultVo> multiDbResultVos = new ArrayList<>();
            List<DbResultVo> judgeDbResultVos = new ArrayList<>();
            /**
             * 遍历获取学生的答题情况
             * */
            for (paperStudentDb paperStudentDb : paperStudentDbs) {
                if(paperStudentDb.getPsdStudent().equals(student.getId()) )
                {
                    if(paperStudentDb.getPsdDbType().equals(1) ){
                        DbResultVo dbResultVo = new DbResultVo();
                        dbResultVo.setScore(single);
                        dbResultVo.setDbId(paperStudentDb.getPsdDb());
                        dbResultVo.setScore(paperStudentDb.getPsdScore());
                        dbResultVo.setDbType(1);
                        singleDbResultVos.add(dbResultVo);
                    }
                    if(paperStudentDb.getPsdDbType().equals(2) ){
                        DbResultVo dbResultVo = new DbResultVo();
                        dbResultVo.setScore(multi);
                        dbResultVo.setDbId(paperStudentDb.getPsdDb());
                        dbResultVo.setScore(paperStudentDb.getPsdScore());
                        dbResultVo.setDbType(2);

                        multiDbResultVos.add(dbResultVo);
                    }
                    if(paperStudentDb.getPsdDbType().equals(3) ){
                        DbResultVo dbResultVo = new DbResultVo();
                        dbResultVo.setScore(judge);
                        dbResultVo.setDbId(paperStudentDb.getPsdDb());
                        dbResultVo.setScore(paperStudentDb.getPsdScore());
                        dbResultVo.setDbType(3);

                        judgeDbResultVos.add(dbResultVo);
                    }
                }
            }
            /**
             * 设置dbResultVo的关联知识点ID
             * */
            for (DbResultVo singleDbResultVo : singleDbResultVos) {
                for (dbKnowledgeTeacher dbKnowledgeTeacher : dbKnowledgeTeachers) {
                    if(singleDbResultVo.getDbId().equals(dbKnowledgeTeacher.getDktDb()))
                    {
                        singleDbResultVo.setRelevanceId(dbKnowledgeTeacher.getDktKnowledge());
                    }
                }
            }
            for (DbResultVo multiDbResultVo : multiDbResultVos) {
                for (dbKnowledgeTeacher dbKnowledgeTeacher : dbKnowledgeTeachers) {
                    if(multiDbResultVo.getDbId().equals(dbKnowledgeTeacher.getDktDb()))
                    {
                        multiDbResultVo.setRelevanceId(dbKnowledgeTeacher.getDktKnowledge());
                    }
                }
            }
            for (DbResultVo judgeDbResultVo : judgeDbResultVos) {
                for (dbKnowledgeTeacher dbKnowledgeTeacher : dbKnowledgeTeachers) {
                    if(judgeDbResultVo.getDbId().equals(dbKnowledgeTeacher.getDktDb()))
                    {
                        judgeDbResultVo.setRelevanceId(dbKnowledgeTeacher.getDktKnowledge());
                    }
                }
            }


            /**
             * 遍历获学生的答题情况,获取各个模块得分，总分，知识点得分
             * 获取每个知识点对应题目对的题目数量，x知识点小分
             * */
            /**
             * 知识点或者课程目标模块分数
             * */
             Double judgeRelevanceSumPoint = 0.0;
            Double singleRelevanceSumPoint = 0.0;
            Double multiRelevanceSumPoint = 0.0;
            /**
             * 各个模块分数
             * */
            Double singleSumPoint = 0.0;
            Double multiSumPoint = 0.0;
            Double judgeSumPoint = 0.0;

            Integer singleRight = 0;
            for (DbResultVo singleDbResultVo : singleDbResultVos) {

                for (int i = 0; i < knowledgeAndProportionVoList.size(); i++) {
                 if(singleDbResultVo.getRelevanceId().equals(knowledgeAndProportionVoList.get(i).getKnowledge().getId()) )
                 {
                     if(!singleDbResultVo.getScore().equals(0.0)){
                         Double eachSingleScore = knowledgeAndProportionVoList.get(i).getSingleEve();
                         singleRelevanceSumPoint = singleRelevanceSumPoint + eachSingleScore;
                         singleSumPoint = singleSumPoint +single;
                     }
                 }
                }
            }
            kpr.setSingleRelevanceSumPoint(singleRelevanceSumPoint);
            kpr.setSingleSumPoint(singleSumPoint);


            for (DbResultVo multiDbResultVo : multiDbResultVos) {
                for (int i = 0; i < knowledgeAndProportionVoList.size(); i++) {
                    if(multiDbResultVo.getRelevanceId().equals(knowledgeAndProportionVoList.get(i).getKnowledge().getId()))
                    {
                        if(!multiDbResultVo.getScore().equals(0.0)){
                            Double eachMultiScore = knowledgeAndProportionVoList.get(i).getMultiEve();
                            multiRelevanceSumPoint = multiRelevanceSumPoint + eachMultiScore;
                            multiSumPoint = multiSumPoint + multi;
                        }
                    }
                }
            }
            kpr.setMultiRelevanceSumPoint(multiRelevanceSumPoint);
            kpr.setMultiSumPoint(multiSumPoint);

            for (DbResultVo judgeDbResultVo : judgeDbResultVos) {
                for (int i = 0; i < knowledgeAndProportionVoList.size(); i++) {
                    if(judgeDbResultVo.getRelevanceId().equals(knowledgeAndProportionVoList.get(i).getKnowledge().getId()))
                    {
                        if(!judgeDbResultVo.getScore().equals(0.0)){
                            Double eachJudgeScore = knowledgeAndProportionVoList.get(i).getJudgeEve();
                            judgeRelevanceSumPoint = judgeRelevanceSumPoint + eachJudgeScore;
                            judgeSumPoint = judgeSumPoint + judge;
                        }
                    }
                }
            }
            kpr.setJudgeRelevanceSumPoint(judgeRelevanceSumPoint);
            kpr.setJudgeSumPoint(judgeSumPoint);

            kpr.setSumPoint(judgeSumPoint+multiSumPoint+singleSumPoint);
            Double RS = judgeRelevanceSumPoint+singleRelevanceSumPoint+multiRelevanceSumPoint;
            kpr.setRelevanceSumPoint(RS);


            studentVo.setSingleDbResultVos(singleDbResultVos);
            StringBuilder sb = new StringBuilder();
            for (DbResultVo singleDbResultVo : singleDbResultVos) {
                sb.append("            "+Math.round(singleDbResultVo.getScore())+" 分 "+"     |       ");
            }
            studentVo.setSingleDbResult(sb.toString());
            sb = new StringBuilder();
            for (DbResultVo multiDbResultVo : multiDbResultVos) {
                sb.append("<pre><pre><pre><pre><pre><pre>"+Math.round(multiDbResultVo.getScore())+" 分 "+"<pre><pre><pre><pre><pre>");
            }
            studentVo.setMultiDbResult(sb.toString());
            sb = new StringBuilder();
            for (DbResultVo judgeDbResultVo : judgeDbResultVos) {
                sb.append("            "+Math.round(judgeDbResultVo.getScore())+" 分 "+"            ");
            }
            studentVo.setMultiDbResult(sb.toString());
            studentVo.setMultiDbResultVos(multiDbResultVos);
            studentVo.setJudgeDbResultVos(judgeDbResultVos);
//            kpr.setSingleDbResultVos(singleDbResultVos);
//            kpr.setMultiDbResultVos(multiDbResultVos);
//            kpr.setJudgeDbResultVos(judgeDbResultVos);

            List<String> list4 = new ArrayList<>();
            list4.add(student.getName());
            list4.add(student.getAccount());
            if(!CollectionUtils.isEmpty(singleDbResultVos)){
            for (DbResultVo singleDbResultVo : singleDbResultVos) {
                list4.add(Math.round(singleDbResultVo.getScore())+"");
            }
            }else {
                for (String s : list1) {
                    list4.add(0+"");
                }
            }
            list4.add(Math.round(singleSumPoint)+"");
            list4.add(Math.round(singleRelevanceSumPoint)+"");
            if(!CollectionUtils.isEmpty(multiDbResultVos)){
            for (DbResultVo multiDbResultVo : multiDbResultVos) {
                list4.add(Math.round(multiDbResultVo.getScore())+"");
            }}
            else {
                for (String s : list2) {
                    list4.add(0+"");
                }
            }
            list4.add(Math.round(multiSumPoint)+"");
            list4.add(Math.round(multiRelevanceSumPoint)+"");
            if(!CollectionUtils.isEmpty(judgeDbResultVos)){
            for (DbResultVo judgeDbResultVo : judgeDbResultVos) {
                list4.add(Math.round(judgeDbResultVo.getScore())+"");
            }
            }else {
                for (String s : list3) {
                    list4.add("0");
                }
            }
            list4.add(Math.round(judgeSumPoint)+"");
            list4.add(Math.round(judgeRelevanceSumPoint)+"");
            list4.add(Math.round((singleSumPoint+multiSumPoint+judgeSumPoint))+"");
            list4.add(Math.round((singleRelevanceSumPoint+multiRelevanceSumPoint+judgeRelevanceSumPoint))+"");
            lists.add(list4);
            kpr.setStudent(studentVo);
            list.add(kpr);

        }



        result.put("final",lists);
        result.put("list",list);
        return Result.success(result);
    }


    @Override
    public Result analyseByTarget(AnalyseByKnowledgeVo params) {
        String classNo = params.getClassNo();
        QueryWrapper<student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cla_no", classNo);
        List<student> studentList = studentMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(studentList)){
            return Result.fail(10001,"该年级没有学生");
        }
        List<Integer> studentIdList = studentList.stream().map(student::getId).collect(Collectors.toList());
        QueryWrapper<paperStudentDb> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("psd_paper",params.getPaperId())
                .in("psd_student",studentIdList);
        /*
         * 获取来的数据包含了得分和答案值（String）,题目类型
         * */
        List<paperStudentDb> paperStudentDbs = paperStudentDbMapper.selectList(queryWrapper1);
        if(CollectionUtils.isEmpty(studentList)){
            return Result.fail(10002,"该试卷没有题目");
        }
        List<Integer> dbIdList = paperStudentDbs.stream().map(paperStudentDb::getPsdDb).collect(Collectors.toList());
        List<Integer> collect = dbIdList.stream().distinct().collect(Collectors.toList());
        /*
       * 根据paperId到paper_target查询target，包好了proportion
       * */
        QueryWrapper<paperTarget> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.in("pt_paper",params.getPaperId());
        List<paperTarget> paperTargets = paperTargetMapper.selectList(queryWrapper2);

        /*
        * 获取target
        * */
        List<Integer> targetIdList = paperTargets.stream().map(paperTarget::getPtTarget).collect(Collectors.toList());
        List<target> targetList = targetMapper.selectBatchIds(targetIdList);

        /*
        * 根据paperId获取paper
        * */
        paper paper = paperMapper.selectById(params.getPaperId());
//        Double single = Double.valueOf(paper.getPapSingleSum()/paper.getPapSingle());
//        Double multi = Double.valueOf(paper.getPapMultiSum()/paper.getPapMulti());
//        Double judge = Double.valueOf(paper.getPapJudgeSum()/paper.getPapJudge());

        /*
        ** 根据dbList和teacherId去dbTargetTeacherMapper查询knowledge
         * */
        QueryWrapper<dbTargetTeacher> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("dt_teacher",params.getTeacherId())
                .in("dt_db",collect);
        List<dbTargetTeacher> dbTargetTeachers = dbTargetTeacherMapper.selectList(queryWrapper3);

        /**
         * 获取各个模块的题量
         * */
        Double papJudge = (double)paper.getPapJudge();
        Double papMulti = (double)paper.getPapMulti();
        Double papSingle = (double)paper.getPapSingle();
        Double papTotal = (double)paper.getPapTotal();

        /**
         * 获取各个模块总分
         * */
        Double papSingleSum = (double)paper.getPapSingleSum();
        Double papMultiSum = (double)paper.getPapMultiSum();
        Double papJudgeSum = (double)paper.getPapJudgeSum();


        Double judge = papJudgeSum/papSingle;
        Double multi = papMultiSum/papMulti;
        Double single = papSingleSum/papJudge;

        Map<String,Object> result = new HashMap<>();

        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        int k = 1 ;
        for(Double i = 0.0;i<papSingle;i++)
        {
            list1.add(String.valueOf(k));
            k++;
        }
        k = 1;
        for(Double i = 0.0;i<papMulti;i++)
        {
            list2.add(String.valueOf(k));
            k++;
        }
        k = 1;
        for(Double i = 0.0;i<papJudge;i++)
        {
            list3.add(String.valueOf(k));
            k++;
        }
        /**
         *
         * */
        List<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("姓名");
        stringArrayList.add("学号");
        for (int i = 0; i < list1.size(); i++) {
            stringArrayList.add((i+1)+"");
        }
        stringArrayList.add("单选题模块得分");
        stringArrayList.add("单选题课程目标得分");

        for (int i = 0; i < list2.size(); i++) {
            stringArrayList.add((i+1)+"");
        }
        stringArrayList.add("多选题模块得分");
        stringArrayList.add("多选题课程目标得分");
        for (int i = 0; i < list3.size(); i++) {
            stringArrayList.add((i+1)+"");
        }
        stringArrayList.add("判断题模块得分");
        stringArrayList.add("判断题课程目标得分");
        stringArrayList.add("试卷总得分");
        stringArrayList.add("课程目标总得分");
        result.put("number",stringArrayList);
        /**
         * 计算每个课程目标对应每个模块的一道题的分值
         * */
        Double singleEve = 0.0;
        Double multiEve = 0.0;
        Double judgeEve = 0.0;
        List<Map<String,Double>> mapList = new ArrayList<>();
        for (paperTarget paperTarget : paperTargets) {
            singleEve = (papSingle*paperTarget.getPtProportion()/(papSingle+papMulti+papJudge)/100)*papTotal/(papSingle*paperTarget.getPtProportion()/100);
            multiEve = (papMulti*paperTarget.getPtProportion()/(papSingle+papMulti+papJudge)/100)*papTotal/(papMulti*paperTarget.getPtProportion()/100);
            judgeEve = (papJudge*paperTarget.getPtProportion()/(papSingle+papMulti+papJudge)/100)*papTotal/(papJudge*paperTarget.getPtProportion()/100);
            Map<String,Double> map = new HashMap<>();
            map.put("singleEve",singleEve);
            map.put("multiEve",multiEve);
            map.put("judgeEve",judgeEve);
            map.put("targetId",Double.valueOf(paperTarget.getPtTarget()));
            mapList.add(map);
        }

        List<TargetAndProportionVo> targetAndProportionVoList = new ArrayList<>();

        for (target target : targetList) {
            for (int i = 0; i < paperTargets.size(); i++) {
                {
                    TargetAndProportionVo vo = new TargetAndProportionVo();
                    vo.setTarget(target);
                    vo.setProportion(paperTargets.get(i).getPtProportion());
                    for (Map<String, Double> map : mapList) {
                        if(map.get("targetId").equals(Double.valueOf( target.getId())) ){
                            vo.setSingleEve(map.get("singleEve"));
                            vo.setMultiEve(map.get("multiEve"));
                            vo.setJudgeEve(map.get("judgeEve"));
                        }
                    }

                    targetAndProportionVoList.add(vo);
                }
            }
        }

        /*
        * 封装列的内容
        * */
        List<List> lists = new ArrayList<>();
        for (student student : studentList) {
            List<String> list4 = new ArrayList<>();
            list4.add(student.getName());
            list4.add(student.getAccount());
            List<DbResultVo> singleDbResultVos = new ArrayList<>();
            List<DbResultVo> multiDbResultVos = new ArrayList<>();
            List<DbResultVo> judgeDbResultVos = new ArrayList<>();
            for (paperStudentDb paperStudentDb : paperStudentDbs) {
                if(paperStudentDb.getPsdStudent().equals(student.getId()) )
                {
                    if(paperStudentDb.getPsdDbType().equals(1) ){
                        DbResultVo dbResultVo = new DbResultVo();
                        dbResultVo.setScore(single);
                        dbResultVo.setDbId(paperStudentDb.getPsdDb());
                        dbResultVo.setScore(paperStudentDb.getPsdScore());
                        dbResultVo.setDbType(1);
                        singleDbResultVos.add(dbResultVo);
                    }
                    if(paperStudentDb.getPsdDbType().equals(2) ){
                        DbResultVo dbResultVo = new DbResultVo();
                        dbResultVo.setScore(multi);
                        dbResultVo.setDbId(paperStudentDb.getPsdDb());
                        dbResultVo.setScore(paperStudentDb.getPsdScore());
                        dbResultVo.setDbType(2);

                        multiDbResultVos.add(dbResultVo);
                    }
                    if(paperStudentDb.getPsdDbType().equals(3) ){
                        DbResultVo dbResultVo = new DbResultVo();
                        dbResultVo.setScore(judge);
                        dbResultVo.setDbId(paperStudentDb.getPsdDb());
                        dbResultVo.setScore(paperStudentDb.getPsdScore());
                        dbResultVo.setDbType(3);

                        judgeDbResultVos.add(dbResultVo);
                    }
                }
            }

            /**
             * 设置dbResultVo的关联知识点ID
             * */
            for (DbResultVo singleDbResultVo : singleDbResultVos) {
                for (dbTargetTeacher dbTargetTeacher : dbTargetTeachers) {
                    if(singleDbResultVo.getDbId().equals(dbTargetTeacher.getDtDb()))
                    {
                        singleDbResultVo.setRelevanceId(dbTargetTeacher.getDtTarget());
                    }
                }
            }
            for (DbResultVo multiDbResultVo : multiDbResultVos) {
                for (dbTargetTeacher dbTargetTeacher : dbTargetTeachers) {
                    if(multiDbResultVo.getDbId().equals(dbTargetTeacher.getDtDb()))
                    {
                        multiDbResultVo.setRelevanceId(dbTargetTeacher.getDtTarget());
                    }
                }
            }
            for (DbResultVo judgeDbResultVo : judgeDbResultVos) {
                for (dbTargetTeacher dbTargetTeacher : dbTargetTeachers) {
                    if(judgeDbResultVo.getDbId().equals(dbTargetTeacher.getDtDb()))
                    {
                        judgeDbResultVo.setRelevanceId(dbTargetTeacher.getDtTarget());
                    }
                }
            }

            /**
             * 遍历获学生的答题情况,获取各个模块得分，总分，知识点得分
             * 获取每个target对应题目对的题目数量，x知识点小分
             * */
            /**
             * target或者课程目标模块分数
             * */
            Double judgeRelevanceSumPoint = 0.0;
            Double singleRelevanceSumPoint = 0.0;
            Double multiRelevanceSumPoint = 0.0;
            /**
             * 各个模块分数
             * */
            Double singleSumPoint = 0.0;
            Double multiSumPoint = 0.0;
            Double judgeSumPoint = 0.0;
            for (DbResultVo singleDbResultVo : singleDbResultVos) {

                for (int i = 0; i < targetAndProportionVoList.size(); i++) {
                    if(singleDbResultVo.getRelevanceId().equals(targetAndProportionVoList.get(i).getTarget().getId()) )
                    {
                        if(!singleDbResultVo.getScore().equals(0.0)){
                            Double eachSingleScore = targetAndProportionVoList.get(i).getSingleEve();
                            singleRelevanceSumPoint = singleRelevanceSumPoint + eachSingleScore;
                            singleSumPoint = singleSumPoint +single;
                        }
                        break;
                    }
                }
            }

            for (DbResultVo multiDbResultVo : multiDbResultVos) {
                for (int i = 0; i < targetAndProportionVoList.size(); i++) {
                    if(multiDbResultVo.getRelevanceId().equals(targetAndProportionVoList.get(i).getTarget().getId()))
                    {
                        if(!multiDbResultVo.getScore().equals(0.0)){
                            Double eachMultiScore = targetAndProportionVoList.get(i).getMultiEve();
                            multiRelevanceSumPoint = multiRelevanceSumPoint + eachMultiScore;
                            multiSumPoint = multiSumPoint + multi;
                        }
                        break;
                    }

                }
            }


            for (DbResultVo judgeDbResultVo : judgeDbResultVos) {
                for (int i = 0; i < targetAndProportionVoList.size(); i++) {
                    if(judgeDbResultVo.getRelevanceId().equals(targetAndProportionVoList.get(i).getTarget().getId()))
                    {
                        if(!judgeDbResultVo.getScore().equals(0.0)){
                            Double eachJudgeScore = targetAndProportionVoList.get(i).getJudgeEve();
                            judgeRelevanceSumPoint = judgeRelevanceSumPoint + eachJudgeScore;
                            judgeSumPoint = judgeSumPoint + judge;
                        }
                        break;
                    }

                }
            }

             if(!CollectionUtils.isEmpty(singleDbResultVos)){
                for (DbResultVo singleDbResultVo : singleDbResultVos) {
                    list4.add(Math.round(singleDbResultVo.getScore())+"");
                }
            }else {
                for (String s : list1) {
                    list4.add(0+"");
                }
            }
            list4.add(Math.round(singleSumPoint)+"");
            list4.add(Math.round(singleRelevanceSumPoint)+"");
            if(!CollectionUtils.isEmpty(multiDbResultVos)){
                for (DbResultVo multiDbResultVo : multiDbResultVos) {
                    list4.add(Math.round(multiDbResultVo.getScore())+"");
                }}
            else {
                for (String s : list2) {
                    list4.add(0+"");
                }
            }
            list4.add(Math.round(multiSumPoint)+"");
            list4.add(Math.round(multiRelevanceSumPoint)+"");
            if(!CollectionUtils.isEmpty(judgeDbResultVos)){
                for (DbResultVo judgeDbResultVo : judgeDbResultVos) {
                    list4.add(Math.round(judgeDbResultVo.getScore())+"");
                }
            }else {
                for (String s : list3) {
                    list4.add("0");
                }
            }
            list4.add(Math.round(judgeSumPoint)+"");
            list4.add(Math.round(judgeRelevanceSumPoint)+"");
            list4.add(Math.round((singleSumPoint+multiSumPoint+judgeSumPoint))+"");
            list4.add(Math.round((singleRelevanceSumPoint+multiRelevanceSumPoint+judgeRelevanceSumPoint))+"");
            lists.add(list4);

        }
        result.put("final",lists);
        return Result.success(result);
    }

    /*
     * 1.根据classId 去student表查出所有该年纪的所有学生
     * 2.studentId,paperId=>wyu_paper_student_db表获取dbId，获取来的数据包含了得分和答案值（String）,题目类型
     * 3.根据paperId 查询paper 可以获取每种题型的体量 总分 可算出每个模块的每小题分数
     *
     * 4.封装
     * */
    @Override
    public Result analyseByRandom(AnalyseByKnowledgeVo params) {
        String classNo = params.getClassNo();
        QueryWrapper<student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cla_no", classNo);
        List<student> studentList = studentMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(studentList)){
            return Result.fail(10001,"该年级没有学生");
        }
        List<Integer> studentIdList = studentList.stream().map(student::getId).collect(Collectors.toList());
        QueryWrapper<paperStudentDb> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("psd_paper",params.getPaperId())
                .in("psd_student",studentIdList);
        /*
         * 获取来的数据包含了得分和答案值（String）,题目类型
         * */
        List<paperStudentDb> paperStudentDbs = paperStudentDbMapper.selectList(queryWrapper1);
        if(CollectionUtils.isEmpty(studentList)){
            return Result.fail(10002,"该试卷没有题目");
        }
        /**
         * 查 paper
         * */

        paper paper = paperMapper.selectById(params.getPaperId());

        /**
         * 获取各个模块的题量
         * */
        Double papJudge = (double)paper.getPapJudge();
        Double papMulti = (double)paper.getPapMulti();
        Double papSingle = (double)paper.getPapSingle();
        Double papTotal = (double)paper.getPapTotal();

        /**
         * 获取各个模块总分
         * */
        Double papSingleSum = (double)paper.getPapSingleSum();
        Double papMultiSum = (double)paper.getPapMultiSum();
        Double papJudgeSum = (double)paper.getPapJudgeSum();

        Double judge = papJudgeSum/papSingle;
        Double multi = papMultiSum/papMulti;
        Double single = papSingleSum/papJudge;
        /**
         * 封装结果
         * */
        Map<String,Object> result = new HashMap<>();



        List<List> listResult = new ArrayList<>();
        for (student student : studentList) {
            List<String> list = new ArrayList<>();
            list.add(student.getName());
            list.add(student.getAccount());
            List<DbResultVo> singleDbResultVos = new ArrayList<>();
            List<DbResultVo> multiDbResultVos = new ArrayList<>();
            List<DbResultVo> judgeDbResultVos = new ArrayList<>();
            /**
             * 遍历获取学生的答题情况
             * */
            for (paperStudentDb paperStudentDb : paperStudentDbs) {
                if(paperStudentDb.getPsdStudent().equals(student.getId()) )
                {
                    if(paperStudentDb.getPsdDbType().equals(1) ){
                        DbResultVo dbResultVo = new DbResultVo();
                        dbResultVo.setScore(single);
                        dbResultVo.setDbId(paperStudentDb.getPsdDb());
                        dbResultVo.setScore(paperStudentDb.getPsdScore());
                        dbResultVo.setDbType(1);
                        singleDbResultVos.add(dbResultVo);
                    }
                    if(paperStudentDb.getPsdDbType().equals(2) ){
                        DbResultVo dbResultVo = new DbResultVo();
                        dbResultVo.setScore(multi);
                        dbResultVo.setDbId(paperStudentDb.getPsdDb());
                        dbResultVo.setScore(paperStudentDb.getPsdScore());
                        dbResultVo.setDbType(2);

                        multiDbResultVos.add(dbResultVo);
                    }
                    if(paperStudentDb.getPsdDbType().equals(3) ){
                        DbResultVo dbResultVo = new DbResultVo();
                        dbResultVo.setScore(judge);
                        dbResultVo.setDbId(paperStudentDb.getPsdDb());
                        dbResultVo.setScore(paperStudentDb.getPsdScore());
                        dbResultVo.setDbType(3);

                        judgeDbResultVos.add(dbResultVo);
                    }
                }
            }
            Double sSum = 0.0;
            if(CollectionUtils.isEmpty(singleDbResultVos)){
                for(int i=0;i<papSingle;i++){
                    list.add("0");
                }
            }else {
            for (DbResultVo singleDbResultVo : singleDbResultVos) {
                list.add(Math.round(singleDbResultVo.getScore())+"");
                sSum = sSum + singleDbResultVo.getScore();
            }
            }
            list.add(Math.round(sSum)+"");
            Double mSum = 0.0;
            if(CollectionUtils.isEmpty(multiDbResultVos)){
                for(int i=0;i<papMulti;i++){
                    list.add("0");
                }
            }else {
            for (DbResultVo multiDbResultVo : multiDbResultVos) {
                list.add(Math.round(multiDbResultVo.getScore())+"");
                mSum = mSum + multiDbResultVo.getScore();
            }
            }
            list.add(Math.round(mSum)+"");
            Double jSum = 0.0;
            if(CollectionUtils.isEmpty(judgeDbResultVos)){
                for(int i=0;i<papJudge;i++){
                    list.add("0");
                }
            }else {
            for (DbResultVo judgeDbResultVo : judgeDbResultVos) {
                list.add(Math.round(judgeDbResultVo.getScore())+"");
                jSum = jSum + judgeDbResultVo.getScore();
            }
            }
            list.add(Math.round(jSum)+"");
            list.add(Math.round(sSum+mSum+jSum)+"");
            listResult.add(list);
        }
        List<String> headList = new ArrayList<>();
        headList.add("姓名");
        headList.add("学号");
        for(int i = 0 ; i<papSingle ; i ++){
            headList.add((i+1)+"");
        }
        headList.add("单选题总分");
        for(int i = 0 ; i<papMulti ; i ++){
            headList.add((i+1)+"");
        }
        headList.add("多选题总分");
        for(int i = 0 ; i<papJudge ; i ++){
            headList.add((i+1)+"");
        }
        headList.add("判断题总分");
        headList.add("试卷总分");
        Map<String,List> map = new HashMap<>();
        map.put("listResult",listResult);
        map.put("headList",headList);
        return Result.success(map);
    }

    @Override
    public Result analyseBySelf(AnalyseByKnowledgeVo params) {
        return null;
    }
}
