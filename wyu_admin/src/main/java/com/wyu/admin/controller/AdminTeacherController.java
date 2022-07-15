package com.wyu.admin.controller;

import com.wyu.admin.dao.pojo.teacher;
import com.wyu.admin.service.AdminTeacherService;
import com.wyu.admin.vo.params.TeacherParams;
import com.wyu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *@CLASSNAME: AdminTeacherController
 *AUTHOR lizhian
 */
@RestController
@RequestMapping
public class AdminTeacherController {
    @Autowired
    private AdminTeacherService adminTeacherService;
    @PostMapping("addAdminTeacher")
    public Result addAdminTeacher(@RequestBody TeacherParams teacherParams){
        teacher teacher = new teacher();
        teacher.setAccount(teacherParams.getAccount());
        teacher.setPassword(teacherParams.getPassword());
        teacher.setName(teacherParams.getName());
        return adminTeacherService.addAdminTeacher(teacher);
    }
}
