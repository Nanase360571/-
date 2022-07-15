package com.wyu.admin.controller;

import com.wyu.admin.service.AdminMajorTeacherCourseService;
import com.wyu.admin.vo.params.SubmitRelationParams;
import com.wyu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ly-lizhian
 */
@RestController
@RequestMapping
public class AdminMajorTeacherCourse {
    @Autowired
    private AdminMajorTeacherCourseService adminMajorTeacherCourseService;

    @PostMapping("/getAllMajor")
    public Result getAllMajor(){

        return adminMajorTeacherCourseService.getAllMajor();
    }
    @PostMapping("/getAllCourse")
    public Result getAllCourse(){

        return adminMajorTeacherCourseService.getAllCourse();
    }

    @PostMapping("/getAllClasses")
    public Result getAllClasses(){

        return adminMajorTeacherCourseService.getAllClasses();
    }
    @PostMapping("/getAllTeacher")
    public Result getAllTeacher(){

        return adminMajorTeacherCourseService.getAllTeacher();
    }

    @PostMapping("/submitRelation")
    public Result submitRelation(@RequestBody SubmitRelationParams params){

        return adminMajorTeacherCourseService.submitRelation(params);
    }
}
