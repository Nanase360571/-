package com.wyu.stu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.common.dao.pojo.course;
import com.wyu.common.dao.pojo.courseTeacherStudent;
import com.wyu.common.dao.pojo.db;
import com.wyu.common.dao.pojo.dbCourse;
import com.wyu.common.util.DealContent;
import com.wyu.common.vo.Result;
import com.wyu.common.vo.dbVoList;
import com.wyu.stu.dao.teacherMapper.CourseMapper;
import com.wyu.stu.dao.teacherMapper.CourseTeacherStudentMapper;
import com.wyu.stu.dao.teacherMapper.DbCourseMapper;
import com.wyu.stu.dao.teacherMapper.DbMapper;
import com.wyu.stu.service.STestService;
import com.wyu.stu.vo.paperVo;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class STestServiceImpl  implements STestService {
    @Autowired
    private CourseTeacherStudentMapper courseTeacherStudentMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private DbCourseMapper dbCourseMapper;
    @Autowired
    private DbMapper dbMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result getCourseTest(Integer studentId) {
        /*
        * 由studentId到courseTeacherStudent表中查询该学生所修的所有课程
        * */
        QueryWrapper<courseTeacherStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cts_student",studentId);
        List<courseTeacherStudent> courseTeacherStudents = courseTeacherStudentMapper.selectList(queryWrapper);
        if(courseTeacherStudents.size() == 0){
            return Result.success(100);
        }
        Collection<Integer> collect = courseTeacherStudents.stream().map(courseTeacherStudent::getCtsCourse).collect(Collectors.toList());
        List<course> courses = courseMapper.selectBatchIds(collect);
        ArrayList<course> list = new ArrayList<>();
        for (course course : courses) {
            if(course.getIsTest() == 1){
                list.add(course);
            }
        }
        return Result.success(list);
    }

    @Override
    public Result randomTest(Integer id) {

        Integer singleEach = 5;
        Integer multiEach = 5;
        Integer judgeEach = 5;
        QueryWrapper<db> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("db_course",id);
        List<db> list = dbMapper.selectList(queryWrapper1);
        if(list.size() == 0)
        {return null;}
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
        if(singleList.size()<singleEach || multiList.size() < multiEach ||judgeList.size()<judgeEach)
        {
            return Result.fail(10001,"题库不足");
        }
        Collections.shuffle(singleList);
        Collections.shuffle(multiList);
        Collections.shuffle(judgeList);
        /*
         *生成随机题目
         * */
        List<db> list1 = singleList.subList(0, singleEach);
        List<db> list2 = multiList.subList(0, multiEach);
        List<db> list3 = judgeList.subList(0, judgeEach);

        List<db> usedList = new ArrayList<>();
        usedList.addAll(list1);
        usedList.addAll(list2);
        usedList.addAll(list3);
        dbVoList content = DealContent.getContent(usedList);
        return Result.success(content);
            }
}
