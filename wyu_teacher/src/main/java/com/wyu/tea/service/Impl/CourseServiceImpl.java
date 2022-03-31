package com.wyu.tea.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wyu.common.dao.pojo.*;
import com.wyu.common.vo.Result;
import com.wyu.tea.dao.mapper.*;
import com.wyu.tea.dao.pojo.student;
import com.wyu.tea.service.CourseService;
import com.wyu.tea.vo.CourseVo;
import com.wyu.tea.vo.params.StudentParams;
import com.wyu.tea.vo.params.StudentToCourseParam;
import com.wyu.tea.vo.params.UpdateKnowledgeParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @PackageName:com.wyu.tea.service.Impl
 * @ClassName:CourseServiceImpl
 * @Description:
 * @author:Aan
 * @data 2022/1/31 16:50
 **/
@Service
@Slf4j
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseTeacherMajorMapper courseTeacherMajorMapper;
    @Autowired
    private CourseKnowledgeTeacherMapper courseKnowledgeTeacherMapper;
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Autowired
    private MajorTargetCourseMapper majorTargetCourseMapper;
    @Autowired
    private TargetMapper targetMapper;
    @Autowired
    private CourseTeacherStudentMapper courseTeacherStudentMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassesMapper classesMapper;



    @Override

    /*
    * 查询该教师的所有课程和对应的课程目标，课程知识点
    *
    * */

    public Result getTeacherCourse(String teacherId) {


        /*根据教师Id查询出课程所有ID*/
        QueryWrapper<courseTeacherMajor> courseTeacherQueryWrapper = new QueryWrapper<>();
        courseTeacherQueryWrapper.eq("ct_teacher",teacherId);
        List<courseTeacherMajor> courseTeacherMajors = courseTeacherMajorMapper.selectList(courseTeacherQueryWrapper);
        /*将查询来的courseTeacher里面的课程ID遍历出来并存储到list中*/
        List<Integer> courseIdList = new ArrayList<>();
        for (courseTeacherMajor courseTeacherMajor : courseTeacherMajors) {
            courseIdList.add(courseTeacherMajor.getCtCourse());
        }
        if(CollectionUtils.isEmpty(courseIdList)){return Result.success(null);}
        courseIdList = courseIdList.stream().distinct().collect(Collectors.toList());



        List<CourseVo> courseVoList = new ArrayList<>();
        for (Integer integer : courseIdList) {
            /*将查询到的一门课程的所有目标和知识点存到ArrayList*/
            CourseVo courseVo = new CourseVo();

            /*按照课程ID查询课程*/
            course course = courseMapper.selectById(integer);
            courseVo.setCourse(course);
            /*
            *知识点查询
            * */
            /*按照课程ID到courseKnowledge表中查询知识点*/
            QueryWrapper<courseKnowledgeTeacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ck_course",integer);
            List<courseKnowledgeTeacher> courseKnowledgeTeacherList = courseKnowledgeTeacherMapper.selectList(queryWrapper);
            List<Integer> list = new ArrayList<>();
            for (courseKnowledgeTeacher courseKnowledgeTeacher : courseKnowledgeTeacherList) {
             list.add(courseKnowledgeTeacher.getCkKnowledge()) ;
            }
            /*将获取的knowledgeIdList到knowledge表中查询*/
            List<knowledge> knowledgeList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(list)){
                knowledgeList = knowledgeMapper.selectBatchIds(list);
            }
            courseVo.setKnowledgeList(knowledgeList);

            /*
            * 课程目标查询
            * */
            /*按照课程ID到courseTarget表中查询课程目标*/
            QueryWrapper<majorTargetCourse> courseTargetQueryWrapper = new QueryWrapper<>();
            courseTargetQueryWrapper.eq("mtc_course",integer);
            List<majorTargetCourse> targets = majorTargetCourseMapper.selectList(courseTargetQueryWrapper);
            list = new ArrayList<>();
            for (majorTargetCourse target : targets) {
                list.add(target.getMtcTarget());
            }
            /*将获取到的TargetIdList到Target表中查询知识点*/
            List<target> targetList =new ArrayList<>();
            if(!CollectionUtils.isEmpty(list)){
                targetList = targetMapper.selectBatchIds(list);
            }
            courseVo.setTargetList(targetList);

            courseVoList.add(courseVo);
        }

//        /*将查询结果封装到TeacherCourseVo中*/
//        TeacherCourseVo teacherCourseVo = new TeacherCourseVo();
//        /*根据教师Id查询出课程所有ID*/
//        QueryWrapper<courseTeacher> courseTeacherQueryWrapper = new QueryWrapper<>();
//        courseTeacherQueryWrapper.eq("ct_teacher",teacherId);
//        List<courseTeacher> courseTeachers = courseTeacherMapper.selectList(courseTeacherQueryWrapper);
//        /*将查询来的courseTeacher里面的课程ID遍历出来并存储到list中*/
//        List<Integer> courseIdList = new ArrayList<>();
//        for (courseTeacher courseTeacher : courseTeachers) {
//            courseIdList.add(courseTeacher.getCtCourse());
//        }
//        if(CollectionUtils.isEmpty(courseIdList)){ return Result.success(teacherCourseVo);}
//        /*将获取的list查询出所有的课程*/
//        log.info("courseMapper.selectBatchIds(courseIdList);");
//        List<course> courses = courseMapper.selectBatchIds(courseIdList);
//        teacherCourseVo.setCourseList(courses);
//
//        /*根据据获取到的课程ID List 到course_Knowledge表中查询知识点ID的List*/
//        log.info("courseKnowledgeMapper.selectBatchIds(courseIdList)");
//        if(CollectionUtils.isEmpty(courseIdList)){ return Result.success(teacherCourseVo);}
//        List<courseKnowledge> courseKnowledgeList = courseKnowledgeMapper.selectBatchIds(courseIdList);
//        List<Integer> KnowledgeIdList = new ArrayList<>();
//        for (courseKnowledge courseKnowledge : courseKnowledgeList) {
//            KnowledgeIdList.add(courseKnowledge.getCkKnowledge());
//        }
//        /*根据查询到的知识点ID list 到knowledge表中查询对应的知识点*/
//        log.info("knowledgeMapper.selectBatchIds(KnowledgeIdList)");
//        if(CollectionUtils.isEmpty(KnowledgeIdList)){return Result.success(teacherCourseVo);}
//        List<knowledge> knowledgeList = knowledgeMapper.selectBatchIds(KnowledgeIdList);
//        teacherCourseVo.setKnowledgeList(knowledgeList);
//
//        /*根据课程ID list到target_course表中查询目标Id list*/
//        log.info("courseTargetMapper.selectBatchIds(courseIdList)");
//        if(CollectionUtils.isEmpty(courseIdList)){return Result.success(teacherCourseVo);}
//        List<courseTarget> targetList = courseTargetMapper.selectBatchIds(courseIdList);
//        List<Integer> targetIdList = new ArrayList<>();
//        for (courseTarget courseTarget : targetList) {
//            targetIdList.add(courseTarget.getCtTarget());
//        }
//        /*根据查询来的targetId list 到target表中查询内容*/
//        log.info("targetMapper.selectBatchIds(targetIdList)");
//        if(CollectionUtils.isEmpty(targetIdList)){return Result.success(teacherCourseVo);}
//        List<target> targets = targetMapper.selectBatchIds(targetIdList);
//        teacherCourseVo.setTargetList(targets);

        return Result.success(courseVoList);
    }

    @Override
    public Result getStudentList(StudentParams studentParams) {
        QueryWrapper<courseTeacherStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cts_course",studentParams.getCourseId())
                .eq("cts_teacher",studentParams.getTeacherId());
        List<courseTeacherStudent> ctsList = courseTeacherStudentMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(ctsList)){return Result.success(null);}
        List<Integer> collect = ctsList.stream().map(courseTeacherStudent::getCtsStudent).collect(Collectors.toList());
        /*
        * 如果collect中有重复值，即，courseTeacherStudent表中存储了两次该学生选这门课的信息，collect中存储了两个该同学的ID值，
        * 然后我们调用studentMapper.selectByIds(collect)时候 mybatis-plus会将重复的剔除了，所以出现一门课有两个同学选了的情况
        * */
        List<student> studentList1 = studentMapper.selectByIds(collect);

        return Result.success(studentList1);
    }

    @Override
    public Result removeStudent(StudentParams studentParams) {
        QueryWrapper<courseTeacherStudent> cts_course = new QueryWrapper<>();
        cts_course.eq("cts_course", studentParams.getCourseId())
                .eq("cts_student",studentParams.getStudentId())
                .eq("cts_teacher",studentParams.getTeacherId());
        int delete = courseTeacherStudentMapper.delete(cts_course);

        return Result.success(delete);
    }


    /*
    * 将学生插入到该门课程中
    * */
    @Override
    @Transactional
    public Result addStudentToCourse(StudentToCourseParam studentToCourseParam) {
        //1.将学生信息插入到学生表，获取他的Id值
        student student =new student();
        BeanUtils.copyProperties(studentToCourseParam,student);
        Integer insert;
        try{
            studentMapper.insert(student);
            insert = student.getId();
            log.info("插入的学生的ID值为"+student.getId());
        }catch (Exception e){
            log.error("插入的学生学号已存在");
            //1.1如果学生信息已经插入过表中，则自行查该学生的Id
            QueryWrapper<student> queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("account",studentToCourseParam.getAccount());
            com.wyu.tea.dao.pojo.student student1 = studentMapper.selectOne(queryWrapper);
            insert = student1.getId();
            log.info("插入的学生的ID值为"+student1.getId());
        }
        //2.将该学生的班级信息存储到班级表中
        classes classes = new classes();
        BeanUtils.copyProperties(studentToCourseParam,classes);
        classesMapper.insertByIgnore(classes);
        log.info("插入的classes的Id"+classes.getId());

        //3.将学生，课程，教师的ID存储到courseTeacherStudent表中
        //3.1查询该学生是否已经在该课程里面了
        QueryWrapper<courseTeacherStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cts_course",studentToCourseParam.getCourseId())
                .eq("cts_student",insert)
                .eq("cts_teacher",studentToCourseParam.getTeacherId());
        courseTeacherStudent selectOne = courseTeacherStudentMapper.selectOne(queryWrapper);
        if(StringUtils.isEmpty(selectOne)){
            courseTeacherStudent courseTeacherStudent = new courseTeacherStudent();
            courseTeacherStudent.setCtsCourse(studentToCourseParam.getCourseId());
            courseTeacherStudent.setCtsStudent(insert);
            courseTeacherStudent.setCtsTeacher(studentToCourseParam.getTeacherId());
            courseTeacherStudentMapper.insert(courseTeacherStudent);
            log.info("courseTeacherStudent插入后的Id值为"+courseTeacherStudent.getId());
            return Result.success(200);
        }

        return Result.fail(10005,"该学生已经在该课程了");


    }

    @Override
    public Result getKnowledge(StudentParams studentParams) {

        return null;
    }

    @Override
    public Result updateKnowledge(UpdateKnowledgeParam updateKnowledgeParam) {
        UpdateWrapper<knowledge> knowledgeUpdateWrapper = new UpdateWrapper<>();
        knowledgeUpdateWrapper.eq("id",updateKnowledgeParam.getId());
        knowledge knowledge = new knowledge();
        knowledge.setKnoCourse(updateKnowledgeParam.getCourseId());
        knowledge.setKnoContent(updateKnowledgeParam.getKnoContent());
        int update = knowledgeMapper.update(knowledge, knowledgeUpdateWrapper);
        return Result.success(200);
    }

    @Override
    @Transactional
    public Result removeKnowledge(UpdateKnowledgeParam updateKnowledgeParam) {
        QueryWrapper<courseKnowledgeTeacher> ckt = new QueryWrapper<>();
        ckt.eq("ck_course",updateKnowledgeParam.getCourseId())
                .eq("ck_knowledge",updateKnowledgeParam.getId())
                .eq("ck_teacher",updateKnowledgeParam.getTeacherId());
        courseKnowledgeTeacherMapper.delete(ckt);
        QueryWrapper<knowledge> knowledgeQueryWrapper = new QueryWrapper<>();
        knowledgeQueryWrapper.eq("kno_content",updateKnowledgeParam.getKnoContent())
                .eq("kno_course",updateKnowledgeParam.getCourseId())
                .eq("id", updateKnowledgeParam.getId());
        knowledgeMapper.delete(knowledgeQueryWrapper);
        return Result.success(200);
    }

    @Override
    @Transactional
    public Result addKnowledgeToCourse(UpdateKnowledgeParam updateKnowledgeParam) {
        /*
        * 将知识点加入带Knowledge表中
        * */
        knowledge knowledge = new knowledge();
        knowledge.setKnoCourse(updateKnowledgeParam.getCourseId());
        knowledge.setKnoContent(updateKnowledgeParam.getKnoContent());
        knowledgeMapper.insert(knowledge);
        /*
        * 将知识点-教师-课程关系插入到表中
        * */
        courseKnowledgeTeacher courseKnowledgeTeacher = new courseKnowledgeTeacher();
        courseKnowledgeTeacher.setCkKnowledge(knowledge.getId());
        courseKnowledgeTeacher.setCkCourse(updateKnowledgeParam.getCourseId());
        courseKnowledgeTeacher.setCkTeacher(updateKnowledgeParam.getTeacherId());
        courseKnowledgeTeacherMapper.insert(courseKnowledgeTeacher);
        return Result.success(knowledge);
    }
}
