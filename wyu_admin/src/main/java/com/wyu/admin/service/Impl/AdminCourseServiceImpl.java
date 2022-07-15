package com.wyu.admin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.admin.dao.mapper.CourseMapper;
import com.wyu.admin.service.AdminCourseService;
import com.wyu.common.dao.pojo.course;
import com.wyu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 *@CLASSNAME: AdminCourseServiceImpl
 *AUTHOR lizhian
 */
@Service
public class AdminCourseServiceImpl implements AdminCourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Result addAdminCourse(course course) {
        try{int insert = courseMapper.insert(course);
            return Result.success(insert);}catch (Exception e){
            return Result.fail(10001,"该课程编号已存在");
        }

    }
}
