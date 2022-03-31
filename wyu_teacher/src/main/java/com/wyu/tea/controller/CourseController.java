package com.wyu.tea.controller;

import com.wyu.common.vo.Result;
import com.wyu.tea.service.CourseService;
import com.wyu.tea.vo.params.StudentParams;
import com.wyu.tea.vo.params.StudentToCourseParam;
import com.wyu.tea.vo.params.UpdateKnowledgeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * @PackageName:com.wyu.tea.controller
 * @ClassName:CourseController
 * @Description:
 * @author:Aan
 * @data 2022/1/31 16:48
 **/
@RestController
@RequestMapping
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/getTeacherCourse")
    public Result getTeacherCourse(@PathParam("teacherId") String teacherId){
        return courseService.getTeacherCourse(teacherId);
    }

    @PostMapping("/getStudentList")
    public Result getStudentList(@RequestBody StudentParams studentParams){
        return courseService.getStudentList(studentParams);
    }

    @PostMapping("/getKnowledge")
    public Result getKnowledge(@RequestBody StudentParams studentParams){
        return courseService.getKnowledge(studentParams);
    }
    @PostMapping("/removeStudent")
    public Result removeStudent(@RequestBody StudentParams studentParams){
        return courseService.removeStudent(studentParams);
    }
    @PostMapping("/addStudentToCourse")
    public Result addStudentToCourse(@RequestBody StudentToCourseParam studentToCourseParam){

        return courseService.addStudentToCourse(studentToCourseParam);
    }

    @PostMapping("/updateKnowledge")
    public Result updateKnowledge(@RequestBody UpdateKnowledgeParam updateKnowledgeParam){
        return courseService.updateKnowledge(updateKnowledgeParam);

    }
    @PostMapping("/removeKnowledge")
    public Result removeKnowledge(@RequestBody UpdateKnowledgeParam updateKnowledgeParam){
        return courseService.removeKnowledge(updateKnowledgeParam);

    }
    @PostMapping("/addKnowledgeToCourse")
    public Result addKnowledgeToCourse(@RequestBody UpdateKnowledgeParam updateKnowledgeParam){
        return courseService.addKnowledgeToCourse(updateKnowledgeParam);

    }
}
