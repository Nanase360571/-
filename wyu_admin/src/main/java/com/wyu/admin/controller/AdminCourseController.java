package com.wyu.admin.controller;

import com.wyu.admin.service.AdminCourseService;
import com.wyu.common.dao.pojo.course;
import com.wyu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *@CLASSNAME: AdminCourseController
 *AUTHOR lizhian
 */
@RestController
@RequestMapping
public class AdminCourseController {
    @Autowired
    private AdminCourseService adminCourseService;
    @PostMapping("addAdminCourse")
    public Result addAdminCourse(@RequestBody course course){
        return adminCourseService.addAdminCourse(course);
    }
}
