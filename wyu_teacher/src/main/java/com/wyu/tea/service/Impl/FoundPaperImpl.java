package com.wyu.tea.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.common.dao.pojo.*;
import com.wyu.common.vo.Result;
import com.wyu.common.vo.dbVo;
import com.wyu.tea.dao.mapper.*;
import com.wyu.tea.service.FoundPaperService;
import com.wyu.tea.vo.params.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RelationSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @PackageName:com.wyu.tea.service.Impl
 * @ClassName:FoundPaperImpl
 * @Description:
 * @author:Aan
 * @data 2022/3/6 23:49
 **/
@Service
public class FoundPaperImpl implements FoundPaperService {
    @Autowired
    private DbKnowledgeTeacherMapper dbKnowledgeTeacherMapper;
    @Autowired
    private DbMapper dbMapper;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private PaperTeacherCourseMapper paperTeacherCourseMapper;
    @Autowired
    private PaperDbMapper paperDbMapper;
    @Autowired
    private PaperTargetMapper paperTargetMapper;
    @Autowired
    private PaperStudentDbMapper paperStudentDbMapper;
    @Autowired
    private CourseTeacherStudentMapper courseTeacherStudentMapper;
    @Autowired
    private DbTargetTeacherMapper dbTargetTeacherMapper;
    @Autowired
    private PaperKnowledgeMapper paperKnowledgeMapper;
    @Autowired
    private CourseTargetTeacherMapper courseTargetTeacherMapper;
    @Autowired
    private TargetMapper targetMapper;
    @Autowired
    private DbTargetPaperMapper dbTargetPaperMapper;
    @Autowired
    private DbCourseMapper dbCourseMapper;
    @Autowired
    private StudentPaperMapper studentPaperMapper;

    @Override
    @Transactional
    public Result FoundByKnowledge(FoundByKnowledgeVo params) {

        /*
        * 判断参数的合法性
        * */

        if(!"".equals(params.getJudgeNumber())&&!"".equals(params.getMultiNumber())&&!"".equals(params.getSingleNumber())&&!"".equals(params.getPaperName())
        &&!"".equals(params.getStartTime())&&!"".equals(params.getTeacherId())&&!"".equals(params.getCourseId())&&!"".equals(params.getEndTime())
                &&!"".equals(params.getPaperSum())&&params.getKnowledgeList()!=null)
        {
            /*
            * 获取每种题型需要的题目量
            * */

            Integer singleEach = params.getSingleSum()/params.getSingleNumber();
            Integer multiEach = params.getMultiSum()/params.getMultiNumber();
            Integer judgeEach = params.getJudgeSum()/params.getJudgeNumber();

            Integer sumKnowledge = 0;
            Integer singleNumber = params.getSingleNumber();
            Integer multiNumber = params.getMultiNumber();
            Integer judgeNumber = params.getJudgeNumber();


            List<List<Integer>> sumEachList = new ArrayList<>();


            /*
            * 获取涉及到的所有知识点
            * */
            List<knowledgeParam> knowledgeParams= params.getKnowledgeList();
            List<knowledgeParam> reallyKnowledgeParamsList= new ArrayList<>();
            for (knowledgeParam knowledgeParam : knowledgeParams) {
                if(knowledgeParam.getProportion() == 0){
                    continue;
                }
                sumKnowledge++;
                reallyKnowledgeParamsList.add(knowledgeParam);
            }

            /*
            * 获取每道题占比*各个题型占比=该知识点对应该知识点多需要的题目，封装成二维列表
            * */
            for (knowledgeParam knowledgeParam : reallyKnowledgeParamsList) {
                List<Integer> eachList = new ArrayList<>();
                Integer tag = knowledgeParam.getProportion()*singleNumber;
                if(tag%100!=0){
                    return Result.fail(1003,"请合理分配知识点，使得每个知识点x题型对应比例是一个整数");
                }
                eachList.add(tag/100);
                tag=knowledgeParam.getProportion()*multiNumber;
                if(tag%100!=0){
                    return Result.fail(1003,"请合理分配知识点，使得每个知识点x题型对应比例是一个整数");
                }
                eachList.add(tag/100);
                tag=knowledgeParam.getProportion()*judgeNumber;
                if(tag%100!=0){
                    return Result.fail(1003,"请合理分配知识点，使得每个知识点x题型对应比例是一个整数");
                }
                eachList.add(tag/100);
                sumEachList.add(eachList);
            }

            /*
            * 到数据库查询知识点所对应的所有题目
            * */
            List<List> existDbList = new ArrayList<>();
            for (knowledgeParam knowledgeParam : reallyKnowledgeParamsList) {
                List<dbKnowledgeTeacher> dbKnowledgeTeacherList = getDbKnowledgeTeacherList(knowledgeParam.getId(), params.getTeacherId());
                if(CollectionUtils.isEmpty(dbKnowledgeTeacherList)){
                    return Result.fail(1002,"请为题目设置对应知识点");
                }
                List<Integer> collect = dbKnowledgeTeacherList.stream().map(dbKnowledgeTeacher::getDktDb).collect(Collectors.toList());
                List<db> dbList = getDbList(collect);
                List<List> completeDbList = getCompleteDbList(dbList);
                existDbList.add(completeDbList);
            }

            /*
            * 检验每个知识点题量是否足够
            * */
            Integer tag = 0 ;
            for (int i = 0; i < sumEachList.size(); i++) {
                List<Integer> list = sumEachList.get(i);
                for (int i1 = 0; i1 < list.size(); i1++) {
                    Integer integer = list.get(i1);
                    List list1 = existDbList.get(i);
                    List list2 = (List) list1.get(i1);
                    if(list2.size() < integer)
                    {
                        tag = 1;
                        break;
                    }
                }
                if(tag == 1){
                    break;
                }
            }
            if(tag == 1)
            {
                return Result.fail(1001,"知识点所对应的题量不足，请合理分配");
            }



            //Integer sum = singleNumber+multiNumber+judgeNumber;
            List<db> list = new ArrayList<>();
            for (int i = 0; i < sumEachList.size(); i++) {
                List<Integer> list1 = sumEachList.get(i);
                for (int i1 = 0; i1 < list1.size(); i1++) {
                    List list2 = existDbList.get(i);
                    List<db> list3 = (List<db>)list2.get(i1);
                    Collections.shuffle(list3);
                    List<db> list4 = list3.subList(0, list1.get(i1));
                    list.addAll(list4);
                }
            }

            /*
            * 将试卷信息存入到paper
            * */
            paper paper = new paper();
            paper.setPapStart(params.getStartTime());
            paper.setPapEnd(params.getEndTime());
            paper.setPapTotal(params.getPaperSum());
            /**
             * 题目量
             * */
            paper.setPapSingle(params.getSingleNumber());
            paper.setPapMulti(params.getMultiNumber());
            paper.setPapJudge(params.getJudgeNumber());

            /**
             * 总分
             * */
            paper.setPapSingleSum(params.getSingleSum());
            paper.setPapMultiSum(params.getMultiSum());
            paper.setPapJudgeSum(params.getJudgeSum());
            paper.setPapFound(params.getPapFound());
            paper.setPapName(params.getPaperName());
            paper.setPapExamTime(params.getExamTime());
            paperMapper.insert(paper);

            /*
            * 将信息插入到Paper_teacher_course表中
            * */
            paperTeacherCourse paperTeacherCourse = new paperTeacherCourse();
            paperTeacherCourse.setPtcPaper(paper.getId());
            paperTeacherCourse.setPtcCourse(params.getCourseId());
            paperTeacherCourse.setPtcTeacher(params.getTeacherId());

            paperTeacherCourseMapper.insert(paperTeacherCourse);

            /*
            * 获取所有筛选的题目ID，将其插入到paperDb表中
            * */
            List<Integer> collect = list.stream().map(db::getId).collect(Collectors.toList());
            List<paperDb> paperDbList = new ArrayList<>();
            for (db db : list) {
                paperDb paperDb = new paperDb();
                paperDb.setPdPaper(paper.getId());
                paperDb.setPdDb(db.getId());
                if(db.getDbType() == 1)
                {

                    paperDb.setPdScore(singleEach);
                }
                if(db.getDbType() == 2)
                {
                    paperDb.setPdScore(multiEach);
                }
                if(db.getDbType() == 3)
                {
                    paperDb.setPdScore(judgeEach);
                }
                paperDbMapper.insert(paperDb);
                paperDbList.add(paperDb);
            }
            /*
            * 插入到paper_Knowledge表中
            * */

            for (knowledgeParam knowledgeParam : reallyKnowledgeParamsList) {
                paperKnowledge paperKnowledge = new paperKnowledge();
                paperKnowledge.setPkPaper(paper.getId());
                paperKnowledge.setPkKnowledge(knowledgeParam.getId());
                paperKnowledge.setPkProportion(knowledgeParam.getProportion());
                paperKnowledgeMapper.insert(paperKnowledge);
            }

            /*
            * 插入到paper_student_db中
            * 先由teacher,course查询到这门课的学生
            * */
            QueryWrapper<courseTeacherStudent> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cts_course",params.getCourseId())
                    .eq("cts_teacher",params.getTeacherId());
            List<courseTeacherStudent> courseTeacherStudents = courseTeacherStudentMapper.selectList(queryWrapper);
            if(CollectionUtils.isEmpty(courseTeacherStudents)){
                return Result.fail(1005,"该门课并没有学生");
            }

            for (db db : list) {
                for (courseTeacherStudent courseTeacherStudent : courseTeacherStudents) {
                    paperStudentDb paperStudentDb = new paperStudentDb();
                    paperStudentDb.setPsdPaper(paper.getId());
                    paperStudentDb.setPsdDb(db.getId());
                    paperStudentDb.setPsdStudent(courseTeacherStudent.getCtsStudent());
                    paperStudentDb.setPsdDbType(db.getDbType());
                    paperStudentDbMapper.insert(paperStudentDb);
                }
            }
            /**
             * 插入到paperStudent表中
             * */
            List<Integer> studentIds = courseTeacherStudents.stream().map(courseTeacherStudent::getCtsStudent).collect(Collectors.toList());
            for (Integer studentId : studentIds) {
                studentPaper studentPaper = new studentPaper();
                studentPaper.setSpStudent(studentId);
                studentPaper.setSpPaper(paper.getId());
                studentPaperMapper.insert(studentPaper);
            }

            return Result.success(200);
        }


        return Result.fail(1006,"参数有错");
    }

    @Override
    @Transactional
    public Result FoundByTarget(FoundByTargetVo params) {

        if(!"".equals(params.getJudgeNumber())&&!"".equals(params.getMultiNumber())&&!"".equals(params.getSingleNumber())&&!"".equals(params.getPaperName())
                &&!"".equals(params.getStartTime())&&!"".equals(params.getTeacherId())&&!"".equals(params.getCourseId())&&!"".equals(params.getEndTime())
                &&!"".equals(params.getPaperSum())&&params.getTargetList()!=null)
        {
            /*
             * 获取每种题型需要的题目量
             * */

            Integer singleEach = params.getSingleSum()/params.getSingleNumber();
            Integer multiEach = params.getMultiSum()/params.getMultiNumber();
            Integer judgeEach = params.getJudgeSum()/params.getJudgeNumber();

            Integer sumKnowledge = 0;
            Integer singleNumber = params.getSingleNumber();
            Integer multiNumber = params.getMultiNumber();
            Integer judgeNumber = params.getJudgeNumber();


            List<List<Integer>> sumEachList = new ArrayList<>();

            /*
             * 获取涉及到的所有课程目标
             * */
            List<targetParam> targetParamList= params.getTargetList();
            List<targetParam> reallyTargetParamsList= new ArrayList<>();
            for (targetParam targetParam : targetParamList) {
                if(targetParam.getProportion() == 0){
                    continue;
                }
                sumKnowledge++;
                reallyTargetParamsList.add(targetParam);
            }

            /*
             * 获取每道题占比*各个题型占比=该知识点对应该知识点多需要的题目，封装成二维列表
             * */
            for (targetParam targetParam : reallyTargetParamsList) {
                List<Integer> eachList = new ArrayList<>();
                Integer tag = targetParam.getProportion()*singleNumber;
                if(tag%100!=0){
                    return Result.fail(1003,"请合理分配课程目标，使得每个课程目标x题型对应比例是一个整数");
                }
                eachList.add(tag/100);
                tag=targetParam.getProportion()*multiNumber;
                if(tag%100!=0){
                    return Result.fail(1003,"请合理分配课程目标，使得每个课程目标x题型对应比例是一个整数");
                }
                eachList.add(tag/100);
                tag=targetParam.getProportion()*judgeNumber;
                if(tag%100!=0){
                    return Result.fail(1003,"请合理分配课程目标，使得每个课程目标x题型对应比例是一个整数");
                }
                eachList.add(tag/100);
                sumEachList.add(eachList);
            }
            /*
             *
             *  到数据库查询知识点所对应的所有题目
             * */
            List<List> existDbList = new ArrayList<>();
            for (targetParam targetParam : reallyTargetParamsList) {
                List<dbTargetTeacher> dbTargetTeacherList = getDbTargetTeacherList(targetParam.getId(), params.getTeacherId());
                if(CollectionUtils.isEmpty(dbTargetTeacherList)){
                    return Result.fail(1002,"请为题目设置对应课程目标");
                }
                List<Integer> collect = dbTargetTeacherList.stream().map(dbTargetTeacher::getDtDb).collect(Collectors.toList());
                List<db> dbList = getDbList(collect);
                List<List> completeDbList = getCompleteDbList(dbList);
                existDbList.add(completeDbList);
            }


            /*
             * 检验每个知识点题量是否足够
             * */
            Integer tag = 0 ;
            for (int i = 0; i < sumEachList.size(); i++) {
                List<Integer> list = sumEachList.get(i);
                for (int i1 = 0; i1 < list.size(); i1++) {
                    Integer integer = list.get(i1);
                    List list1 = existDbList.get(i);
                    List list2 = (List) list1.get(i1);
                    if(list2.size() < integer)
                    {
                        tag = 1;
                        break;
                    }
                }
                if(tag == 1){
                    break;
                }
            }
            if(tag == 1)
            {
                return Result.fail(1001,"课程目标所对应的题量不足，请合理分配");
            }


            //Integer sum = singleNumber+multiNumber+judgeNumber;
            List<db> list = new ArrayList<>();
            for (int i = 0; i < sumEachList.size(); i++) {
                List<Integer> list1 = sumEachList.get(i);
                for (int i1 = 0; i1 < list1.size(); i1++) {
                    List list2 = existDbList.get(i);
                    List<db> list3 = (List<db>)list2.get(i1);
                    Collections.shuffle(list3);
                    List<db> list4 = list3.subList(0, list1.get(i1));
                    list.addAll(list4);
                }
            }
            /*
             * 将试卷信息存入到paper
             * */
            paper paper = new paper();
            paper.setPapStart(params.getStartTime());
            paper.setPapEnd(params.getEndTime());
            paper.setPapTotal(params.getPaperSum());
            /**
             * 题目量
             * */
            paper.setPapSingle(params.getSingleNumber());
            paper.setPapMulti(params.getMultiNumber());
            paper.setPapJudge(params.getJudgeNumber());

            /**
             * 总分
             * */
            paper.setPapSingleSum(params.getSingleSum());
            paper.setPapMultiSum(params.getMultiSum());
            paper.setPapJudgeSum(params.getJudgeSum());
            paper.setPapName(params.getPaperName());
            paper.setPapFound(params.getPapFound());
            paper.setPapExamTime(params.getExamTime());
            paperMapper.insert(paper);

            /*
             * 将信息插入到Paper_teacher_course表中
             * */
            paperTeacherCourse paperTeacherCourse = new paperTeacherCourse();
            paperTeacherCourse.setPtcPaper(paper.getId());
            paperTeacherCourse.setPtcCourse(params.getCourseId());
            paperTeacherCourse.setPtcTeacher(params.getTeacherId());

            paperTeacherCourseMapper.insert(paperTeacherCourse);





            /*
             * 获取所有筛选的题目ID，将其插入到paperDb表中
             * */
            List<Integer> collect = list.stream().map(db::getId).collect(Collectors.toList());
            List<paperDb> paperDbList = new ArrayList<>();
            for (db db : list) {
                paperDb paperDb = new paperDb();
                paperDb.setPdPaper(paper.getId());
                paperDb.setPdDb(db.getId());
                if(db.getDbType() == 1)
                {

                    paperDb.setPdScore(singleEach);
                }
                if(db.getDbType() == 2)
                {
                    paperDb.setPdScore(multiEach);
                }
                if(db.getDbType() == 3)
                {
                    paperDb.setPdScore(judgeEach);
                }
                paperDbMapper.insert(paperDb);


            }
            /*
             * 插入到paper_Target表中
             * */

            for (targetParam targetParam : reallyTargetParamsList) {
                paperTarget paperTarget = new paperTarget();
                paperTarget.setPtPaper(paper.getId());
                paperTarget.setPtTarget(targetParam.getId());
                paperTarget.setPtProportion(targetParam.getProportion());
                paperTargetMapper.insert(paperTarget);




            }

            /*
             * 插入到paper_student_db中
             * 先由teacher,course查询到这门课的学生
             * */
            QueryWrapper<courseTeacherStudent> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cts_course",params.getCourseId())
                    .eq("cts_teacher",params.getTeacherId());
            List<courseTeacherStudent> courseTeacherStudents = courseTeacherStudentMapper.selectList(queryWrapper);
            if(CollectionUtils.isEmpty(courseTeacherStudents)){
                return Result.fail(1005,"该门课并没有学生");
            }

//            for (int i = 0; i < list.size(); i++) {
//                paperStudentDb paperStudentDb = new paperStudentDb();
//                paperStudentDb.setPsdPaper(paper.getId());
//                paperStudentDb.setPsdDb(list.get(i).getId());
//                paperStudentDb.setPsdStudent(courseTeacherStudents.get(i).getCtsStudent());
//                paperStudentDbMapper.insert(paperStudentDb);
//            }
            for (db db : list) {
                for (courseTeacherStudent courseTeacherStudent : courseTeacherStudents) {
                    paperStudentDb paperStudentDb = new paperStudentDb();
                    paperStudentDb.setPsdPaper(paper.getId());
                    paperStudentDb.setPsdDb(db.getId());
                    paperStudentDb.setPsdStudent(courseTeacherStudent.getCtsStudent());
                    paperStudentDb.setPsdDbType(db.getDbType());
                    paperStudentDbMapper.insert(paperStudentDb);
                }
            }

            /**
             * 插入到paperStudent表中
             * */
            List<Integer> studentIds = courseTeacherStudents.stream().map(courseTeacherStudent::getCtsStudent).collect(Collectors.toList());
            for (Integer studentId : studentIds) {
                studentPaper studentPaper = new studentPaper();
                studentPaper.setSpStudent(studentId);
                studentPaper.setSpPaper(paper.getId());
                studentPaperMapper.insert(studentPaper);
            }
            return Result.success(200);
        }

        return Result.fail(1006,"参数有错");

    }
    /*
     * 随机组卷
     *   1.查询所有题库
     *   2.随机抽取定量得题库
     *
     * */

    @Override
    @Transactional
    public Result FoundByRandom(FoundByTargetVo params) {
        if(!"".equals(params.getJudgeNumber())&&!"".equals(params.getMultiNumber())&&!"".equals(params.getSingleNumber())&&!"".equals(params.getPaperName())
                &&!"".equals(params.getStartTime())&&!"".equals(params.getTeacherId())&&!"".equals(params.getCourseId())&&!"".equals(params.getEndTime())
                &&!"".equals(params.getPaperSum()))
        {
            /*
             * 获取每种题型需要的题目量
             * */

            Integer singleEach = params.getSingleSum()/params.getSingleNumber();
            Integer multiEach = params.getMultiSum()/params.getMultiNumber();
            Integer judgeEach = params.getJudgeSum()/params.getJudgeNumber();

            Integer singleNumber = params.getSingleNumber();
            Integer multiNumber = params.getMultiNumber();
            Integer judgeNumber = params.getJudgeNumber();


            List<List<Integer>> sumEachList = new ArrayList<>();

           QueryWrapper<db> dbQueryWrapper = new QueryWrapper<>();
            dbQueryWrapper.eq("db_course", params.getCourseId());
            List<db> list = dbMapper.selectList(dbQueryWrapper);
            //List<db> list = dbMapper.selectBatchIds(collect);

            List<db> singleList = new ArrayList<>();
            List<db> multiList = new ArrayList<>();
            List<db> judgeList = new ArrayList<>();

            for (db db : list) {
                if(db.getDbType() == 1){
                    singleList.add(db);
                }if(db.getDbType() == 2){
                    multiList.add(db);
                }if(db.getDbType() == 3){
                    judgeList.add(db);
                }
            }
            /*
            * 检验题目数量是否足够
            * */
            if(singleList.size()<params.getSingleNumber() || multiList.size() < params.getMultiNumber() ||judgeList.size()<params.getJudgeNumber())
            {
                return Result.fail(10001,"题库不足");
            }
            Collections.shuffle(singleList);
            Collections.shuffle(multiList);
            Collections.shuffle(judgeList);

            /*
            *生成随机题目
            * */
            List<db> list1 = singleList.subList(0, params.getSingleNumber());
            List<db> list2 = multiList.subList(0, params.getMultiNumber());
            List<db> list3 = judgeList.subList(0, params.getJudgeNumber());

            List<db> usedList = new ArrayList<>();
            usedList.addAll(list1);
            usedList.addAll(list2);
            usedList.addAll(list3);

            /*
             * 将试卷信息存入到paper
             * */
            paper paper = new paper();
            paper.setPapStart(params.getStartTime());
            paper.setPapEnd(params.getEndTime());
            paper.setPapTotal(params.getPaperSum());
            /**
             * 题目量
             * */
            paper.setPapSingle(params.getSingleNumber());
            paper.setPapMulti(params.getMultiNumber());
            paper.setPapJudge(params.getJudgeNumber());

            /**
             * 总分
             * */
            paper.setPapSingleSum(params.getSingleSum());
            paper.setPapMultiSum(params.getMultiSum());
            paper.setPapJudgeSum(params.getJudgeSum());
            paper.setPapName(params.getPaperName());
            paper.setPapFound(params.getPapFound());
            paper.setPapExamTime(params.getExamTime());
            paperMapper.insert(paper);

            /*
             * 将信息插入到Paper_teacher_course表中
             * */
            paperTeacherCourse paperTeacherCourse = new paperTeacherCourse();
            paperTeacherCourse.setPtcPaper(paper.getId());
            paperTeacherCourse.setPtcCourse(params.getCourseId());
            paperTeacherCourse.setPtcTeacher(params.getTeacherId());

            paperTeacherCourseMapper.insert(paperTeacherCourse);

            /*
             * 获取所有筛选的题目ID，将其插入到paperDb表中
             * */
            List<paperDb> paperDbList = new ArrayList<>();
            for (db db : usedList) {
                paperDb paperDb = new paperDb();
                paperDb.setPdPaper(paper.getId());
                paperDb.setPdDb(db.getId());
                if(db.getDbType() == 1)
                {

                    paperDb.setPdScore(singleEach);
                }
                if(db.getDbType() == 2)
                {
                    paperDb.setPdScore(multiEach);
                }
                if(db.getDbType() == 3)
                {
                    paperDb.setPdScore(judgeEach);
                }
                paperDbMapper.insert(paperDb);
                paperDbList.add(paperDb);
            }

            /*
             * 插入到paper_student_db中
             * 先由teacher,course查询到这门课的学生
             * */
            QueryWrapper<courseTeacherStudent> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("cts_course",params.getCourseId())
                    .eq("cts_teacher",params.getTeacherId());
            List<courseTeacherStudent> courseTeacherStudents = courseTeacherStudentMapper.selectList(queryWrapper1);
            if(CollectionUtils.isEmpty(courseTeacherStudents)){
                return Result.fail(1005,"该门课并没有学生");
            }

//            for (int i = 0; i < list.size(); i++) {
//                paperStudentDb paperStudentDb = new paperStudentDb();
//                paperStudentDb.setPsdPaper(paper.getId());
//                paperStudentDb.setPsdDb(list.get(i).getId());
//                paperStudentDb.setPsdStudent(courseTeacherStudents.get(i).getCtsStudent());
//                paperStudentDbMapper.insert(paperStudentDb);
//            }
            ArrayList<db> list4 = new ArrayList<>();
            list4.addAll(list1);
            list4.addAll(list2);
            list4.addAll(list3);
            for (db db : list4) {
                for (courseTeacherStudent courseTeacherStudent : courseTeacherStudents) {
                    paperStudentDb paperStudentDb = new paperStudentDb();
                    paperStudentDb.setPsdPaper(paper.getId());
                    paperStudentDb.setPsdDb(db.getId());
                    paperStudentDb.setPsdStudent(courseTeacherStudent.getCtsStudent());
                    paperStudentDb.setPsdDbType(db.getDbType());
                    paperStudentDbMapper.insert(paperStudentDb);
                }
            }

            /**
             * 插入到paperStudent表中
             * */
            List<Integer> studentIds = courseTeacherStudents.stream().map(courseTeacherStudent::getCtsStudent).collect(Collectors.toList());
            for (Integer studentId : studentIds) {
                studentPaper studentPaper = new studentPaper();
                studentPaper.setSpStudent(studentId);
                studentPaper.setSpPaper(paper.getId());
                studentPaperMapper.insert(studentPaper);
            }
            return Result.success(200);
        }

        return Result.fail(1006,"参数有错");
    }

    @Override
    @Transactional
    public Result FoundBySelf(FoundBySelfVo params) {
        if(!"".equals(params.getJudgeNumber())&&!"".equals(params.getMultiNumber())&&!"".equals(params.getSingleNumber())&&!"".equals(params.getPaperName())
                &&!"".equals(params.getStartTime())&&!"".equals(params.getTeacherId())&&!"".equals(params.getCourseId())&&!"".equals(params.getEndTime())
                &&!"".equals(params.getPaperSum())){
            /*
             * 获取每种题型的单个分数
             * */
            Integer singlePoint = params.getSingleSum()/params.getSingleSum();
            Integer multiPoint = params.getMultiSum()/params.getMultiNumber();
            Integer judgePoint = params.getJudgeSum()/params.getJudgeNumber();

            /*
             * 将试卷信息存入到paper
             * */
            paper paper = new paper();
            paper.setPapStart(params.getStartTime());
            paper.setPapEnd(params.getEndTime());
            paper.setPapTotal(params.getPaperSum());
            /**
             * 题目量
             * */
            paper.setPapSingle(params.getSingleNumber());
            paper.setPapMulti(params.getMultiNumber());
            paper.setPapJudge(params.getJudgeNumber());

            /**
             * 总分
             * */
            paper.setPapSingleSum(params.getSingleSum());
            paper.setPapMultiSum(params.getMultiSum());
            paper.setPapJudgeSum(params.getJudgeSum());

            paper.setPapName(params.getPaperName());
            paper.setPapFound(params.getPapFound());
            paper.setPapFound(params.getPapFound());
            paper.setPapExamTime(params.getExamTime());
            paperMapper.insert(paper);
            /*
             * 将信息插入到Paper_teacher_course表中
             * */
            paperTeacherCourse paperTeacherCourse = new paperTeacherCourse();
            paperTeacherCourse.setPtcPaper(paper.getId());
            paperTeacherCourse.setPtcCourse(params.getCourseId());
            paperTeacherCourse.setPtcTeacher(params.getTeacherId());

            paperTeacherCourseMapper.insert(paperTeacherCourse);
            /*
             * 获取所有的题目ID，将其插入到paperDb表中
             * */
            List<dbVo> dbVoList = params.getDbList();
            if(dbVoList.size() == 0)
            {
                return Result.fail(10003,"题目不能为空");
            }
            for (dbVo dbVo : dbVoList) {
                paperDb paperDb = new paperDb();
                paperDb.setPdPaper(paper.getId());
                paperDb.setPdDb(dbVo.getId());
                if(dbVo.getDbType() == 1)
                {

                    paperDb.setPdScore(singlePoint);
                }
                if(dbVo.getDbType() == 2)
                {
                    paperDb.setPdScore(multiPoint);
                }
                if(dbVo.getDbType() == 3)
                {
                    paperDb.setPdScore(judgePoint);
                }
                paperDbMapper.insert(paperDb);

            }
            /*
             * 插入到paper_student_db中
             * 先由teacher,course查询到这门课的学生
             * */
            QueryWrapper<courseTeacherStudent> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("cts_course",params.getCourseId())
                    .eq("cts_teacher",params.getTeacherId());
            List<courseTeacherStudent> courseTeacherStudents = courseTeacherStudentMapper.selectList(queryWrapper1);
            if(CollectionUtils.isEmpty(courseTeacherStudents)){
                return Result.fail(1005,"该门课并没有学生");
            }

//            for (int i = 0; i < dbVoList.size(); i++) {
//                paperStudentDb paperStudentDb = new paperStudentDb();
//                paperStudentDb.setPsdPaper(paper.getId());
//                paperStudentDb.setPsdDb(dbVoList.get(i).getId());
//                paperStudentDb.setPsdStudent(courseTeacherStudents.get(i).getCtsStudent());
//                paperStudentDbMapper.insert(paperStudentDb);
//            }
            for (dbVo dbVo : dbVoList) {
                for (courseTeacherStudent courseTeacherStudent : courseTeacherStudents) {
                    paperStudentDb paperStudentDb = new paperStudentDb();
                    paperStudentDb.setPsdPaper(paper.getId());
                    paperStudentDb.setPsdDb(dbVo.getId());
                    paperStudentDb.setPsdStudent(courseTeacherStudent.getCtsStudent());
                    paperStudentDb.setPsdDbType(dbVo.getDbType());
                    paperStudentDbMapper.insert(paperStudentDb);
                }
            }
            /**
             * 插入到paperStudent表中
             * */
            List<Integer> studentIds = courseTeacherStudents.stream().map(courseTeacherStudent::getCtsStudent).collect(Collectors.toList());
            for (Integer studentId : studentIds) {
                studentPaper studentPaper = new studentPaper();
                studentPaper.setSpStudent(studentId);
                studentPaper.setSpPaper(paper.getId());
                studentPaperMapper.insert(studentPaper);
            }
            return Result.success(200);
        }
        return Result.fail(10006,"参数不合法");
    }

    @Override
    public Result getAllDb(Integer courseId, Integer teacherId) {
//        QueryWrapper<dbCourse> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("dc_course" , courseId);
//        List<dbCourse> dbCourseList = dbCourseMapper.selectList(queryWrapper);
//        if(dbCourseList.size() == 0)
//        {
//            return Result.fail(10001,"该课程没有题库");
//        }
//        List<Integer> collect = dbCourseList.stream().map(dbCourse::getDcDb).collect(Collectors.toList());
//        List<db> list = dbMapper.selectBatchIds(collect);
//
//        QueryWrapper<db> queryWrapper1 = new QueryWrapper<>();
//        queryWrapper1.eq("db_course",courseId);
//        List<db> dbs = dbMapper.selectList(queryWrapper1);
        QueryWrapper<db> dbQueryWrapper = new QueryWrapper<>();
        dbQueryWrapper.eq("db_course",courseId);
        List<db> list1 = dbMapper.selectList(dbQueryWrapper);



        return Result.success(list1);
    }



    @Override
    public Result getAllTargetList(Integer courseId, Integer teacherId) {
        QueryWrapper<courseTargetTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ctt_course",courseId)
                .eq("ctt_teacher",teacherId);
        List<courseTargetTeacher> courseTargetTeachers = courseTargetTeacherMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(courseTargetTeachers))
        {
            return Result.success(200);
        }
        List<Integer> collect = courseTargetTeachers.stream().map(courseTargetTeacher::getCttTarget).collect(Collectors.toList());
        List<target> targetList = targetMapper.selectBatchIds(collect);
        return Result.success(targetList);
    }



    public List<dbKnowledgeTeacher> getDbKnowledgeTeacherList(Integer knowledgeId,Integer teacherId){
        QueryWrapper<dbKnowledgeTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dkt_knowledge",knowledgeId)
                .eq("dkt_teacher",teacherId);
        List<dbKnowledgeTeacher> dbKnowledgeTeachers = dbKnowledgeTeacherMapper.selectList(queryWrapper);
        return dbKnowledgeTeachers;

    }
    public List<dbTargetTeacher> getDbTargetTeacherList(Integer targetId,Integer teacherId){
        QueryWrapper<dbTargetTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dt_target",targetId)
                .eq("dt_teacher",teacherId);
        List<dbTargetTeacher> dbTargetTeachers = dbTargetTeacherMapper.selectList(queryWrapper);
        return dbTargetTeachers;

    }
    public List<db> getDbList(List<Integer> idList){
        List<db> dbs = dbMapper.selectBatchIds(idList);
        return dbs;

    }
    public List getCompleteDbList(List<db> dbList)
    {
        List<List> list = new ArrayList<>();
        List<db> singleDbList = new ArrayList<>();
        List<db> multiDbList = new ArrayList();
        List<db> judgeDbList = new ArrayList();
        for (db db : dbList) {

            if(db.getDbType() == 1){
                singleDbList.add(db);
            }
            if(db.getDbType() == 2)
            {
                multiDbList.add(db);
            }
            if(db.getDbType() == 3)
            {
                judgeDbList.add(db);
            }


        }
        list.add(singleDbList);
        list.add(multiDbList);
        list.add(judgeDbList);

        return list;
    }




}
