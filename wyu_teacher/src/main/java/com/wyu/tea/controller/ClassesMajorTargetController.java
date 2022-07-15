package com.wyu.tea.controller;

import com.wyu.common.vo.Result;
import com.wyu.tea.service.ClassesMajorTargetService;
import com.wyu.tea.vo.params.RemoveAndAddMajorClassesParam;
import com.wyu.tea.vo.params.SetKnowledgeForCourse;
import com.wyu.tea.vo.params.SetTargetForMajorParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @PackageName:com.wyu.tea.controller
 * @ClassName:ClassesMajorTargetController
 * @Description:
 * @author:Aan
 * @data 2022/2/15 18:22
 **/
@RequestMapping
@RestController
public class ClassesMajorTargetController {

    @Autowired
    private ClassesMajorTargetService classesMajorTargetService;

    @GetMapping("/getMajorList")
    public Result getMajorList(){
        return classesMajorTargetService.getMajorList();
    }
    @GetMapping("/getMajorClasses")
    public Result getMajorClasses(@RequestParam("majorId") Integer majorId){
        return classesMajorTargetService.getMajorClasses(majorId);
    }
    @GetMapping("/getLeftClasses")
    public Result getLeftClasses(@RequestParam("majorId") Integer majorId){
        return classesMajorTargetService.getLeftClasses(majorId);
    }
    @PostMapping("/removeMajorClasses")
    public Result removeMajorClasses(@RequestBody RemoveAndAddMajorClassesParam param){
        return classesMajorTargetService.removeMajorClasses(param);
    }
    @PostMapping("/addClassesToMajor")
    public Result addClassesToMajor(@RequestBody RemoveAndAddMajorClassesParam param){
        return classesMajorTargetService.addClassesToMajor(param);
    }
    @GetMapping("/getCourseList")
    public Result getCourseList(@RequestParam("teacherId")Integer teacherId,
                                @RequestParam("majorId")Integer majorId)
    {
        return classesMajorTargetService.getCourseList(teacherId,majorId);

    }
    @PostMapping("/getTargetList")
    public Result getTargetList(@RequestBody RemoveAndAddMajorClassesParam param)
    {
        return classesMajorTargetService.getTargetList(param);
    }
    @PostMapping("/setTargetForMajor")
    public Result setTargetForMajor(@RequestBody SetTargetForMajorParam param){
        return  classesMajorTargetService.setTargetForMajor(param);
    }
//    @GetMapping("/getKnowledgeList")
//    public Result getKnowledgeList(@RequestParam("courseId")Integer courseId,
//                                   @RequestParam("teacherId")Integer teacherId){
//        return  classesMajorTargetService.getKnowledgeList(courseId,teacherId);
//    }
    @PostMapping("setKnowledgeForCourse")
    public Result setKnowledgeForCourse(@RequestBody SetKnowledgeForCourse param){
        return  classesMajorTargetService.setKnowledgeForCourse(param);
    }

}
