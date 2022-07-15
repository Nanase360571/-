package com.wyu.admin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.admin.dao.mapper.*;
import com.wyu.admin.dao.pojo.teacher;
import com.wyu.admin.service.AdminMajorTeacherCourseService;
import com.wyu.admin.vo.params.SubmitRelationParams;
import com.wyu.common.dao.pojo.classes;
import com.wyu.common.dao.pojo.course;
import com.wyu.common.dao.pojo.courseTeacherMajor;
import com.wyu.common.dao.pojo.major;
import com.wyu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ly-lizhian
 */ /*
 *@CLASSNAME: AdminMajorTeacherCourseImpl
 *AUTHOR lizhian
 */
@Service
public class AdminMajorTeacherCourseImpl implements AdminMajorTeacherCourseService {

    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private ClassesMapper classesMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseTeacherMajorMapper courseTeacherMajorMapper;

    @Override
    public Result getAllMajor() {
        QueryWrapper<major> queryWrapper = new QueryWrapper<major>();

        return Result.success(majorMapper.selectList(queryWrapper));
    }

    @Override
    public Result getAllClasses() {
        QueryWrapper<classes> queryWrapper = new QueryWrapper<classes>();
        return Result.success(classesMapper.selectList(queryWrapper));
    }

    @Override
    public Result getAllTeacher() {
        QueryWrapper<teacher> queryWrapper = new QueryWrapper<>();
        return Result.success(teacherMapper.selectList(queryWrapper));
    }

    @Override
    public Result submitRelation(SubmitRelationParams params) {
        try{for (Integer integer : params.getTeacherValue()) {
            courseTeacherMajor courseTeacherMajor = new courseTeacherMajor();
            courseTeacherMajor.setCtCourse(params.getCourseValues());
            courseTeacherMajor.setCtMajor(params.getMajorValue());
            courseTeacherMajor.setCtTeacher(integer);
            courseTeacherMajorMapper.insert(courseTeacherMajor);
        }
            return Result.success(200);}
        catch (Exception e){
            return Result.fail(10001,"插入失败,已存在对应关系");
        }

    }

    @Override
    public Result getAllCourse() {
        QueryWrapper<course> queryWrapper = new QueryWrapper<>();

        return Result.success(courseMapper.selectList(queryWrapper));
    }
}
