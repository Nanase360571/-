package com.wyu.admin.service.Impl;

import com.wyu.admin.dao.mapper.TeacherMapper;
import com.wyu.admin.dao.pojo.teacher;
import com.wyu.admin.service.AdminTeacherService;
import com.wyu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 *@CLASSNAME: AdminTeacherServiceImpl
 *AUTHOR lizhian
 */
@Service
public class AdminTeacherServiceImpl implements AdminTeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public Result addAdminTeacher(teacher teacher) {
        try{return Result.success(teacherMapper.insert(teacher));}catch (Exception e){
            return Result.fail(10001,"该编号已存在");
        }

    }
}
