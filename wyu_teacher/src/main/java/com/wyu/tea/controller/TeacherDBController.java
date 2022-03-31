package com.wyu.tea.controller;

import com.wyu.common.vo.Result;
import com.wyu.tea.service.TeacherDBService;
import com.wyu.tea.vo.params.UpdateKnowledgeAndTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * @PackageName:com.wyu.tea.controller
 * @ClassName:TeacherDBController
 * @Description:
 * @author:Aan
 * @data 2022/2/24 19:21
 **/
@RestController
@RequestMapping
public class TeacherDBController {

    @Autowired
    private TeacherDBService teacherDBService;
    @GetMapping("getCourseDB")
    public Result getCourseDB(@PathParam("courseId") Integer courseId,
                              @PathParam("teacherId") Integer teacherId)
    {
        return teacherDBService.getCourseDB(courseId,teacherId);
    }
    @GetMapping("getTargetList")
    public Result getTargetList(@PathParam("courseId") Integer courseId,
                              @PathParam("teacherId") Integer teacherId)
    {
        return teacherDBService.getTargetList(courseId,teacherId);
    }
    @GetMapping("getKnowledgeList")
    public Result getKnowledgeList(@PathParam("courseId") Integer courseId,
                              @PathParam("teacherId") Integer teacherId)
    {
        return teacherDBService.getKnowledgeList(courseId,teacherId);
    }
    @PostMapping("updateKnowledgeAndTarget")
    public Result updateKnowledgeAndTarget(@RequestBody UpdateKnowledgeAndTarget updateKnowledgeAndTarget){

        return teacherDBService.updateKnowledgeAndTarget(updateKnowledgeAndTarget);
    }
}
