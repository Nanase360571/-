package com.wyu.tea.controller;

import com.wyu.common.dao.pojo.db;
import com.wyu.common.vo.Result;
import com.wyu.tea.poiReadExcel.poiReadExcel;
import com.wyu.tea.service.TeacherDBService;
import com.wyu.tea.util.MultipartFileToFile;
import com.wyu.tea.vo.AddDBVo;
import com.wyu.tea.vo.params.UpdateKnowledgeAndTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.File;
import java.util.List;
import java.util.Map;

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
    @PostMapping("addDB")
    public Result addDB(@RequestBody AddDBVo AddDBVo){

        return teacherDBService.addBD(AddDBVo);
    }
    @PostMapping("addPatchDb")
    @Transactional
    public Result addPatchDb(@RequestParam Map<String, String> map,MultipartFile file) throws Exception {
        Integer courseId = Integer.parseInt(map.get("courseId"));
        System.out.println(file.getOriginalFilename());
        File file1 = MultipartFileToFile.multipartFileToFile(file);
        String absolutePath = file1.getAbsolutePath();
        List<db> dbs = poiReadExcel.readDb(courseId,absolutePath);
        return teacherDBService.addPatchDb(dbs);
    }
    @GetMapping("getAllCourse")
    public Result getAllCourse(@PathParam("teacherId") Integer teacherId)
    {
        return teacherDBService.getAllCourse(teacherId);
    }
}
