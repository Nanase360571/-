package com.wyu.tea.controller;

import com.wyu.common.vo.Result;
import com.wyu.tea.service.FoundPaperService;
import com.wyu.tea.vo.params.FoundByKnowledgeVo;
import com.wyu.tea.vo.params.FoundBySelfVo;
import com.wyu.tea.vo.params.FoundByTargetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationSupport;
import javax.websocket.server.PathParam;

/**
 * @PackageName:com.wyu.tea.controller
 * @ClassName:FoundPaperController
 * @Description:
 * @author:Aan
 * @data 2022/3/6 16:18
 **/
@RestController
@RequestMapping
public class FoundPaperController {

    @Autowired
    private FoundPaperService foundPaperService;

    @PostMapping("/FoundByKnowledge")
    public Result FoundByKnowledge(@RequestBody FoundByKnowledgeVo params){

        return foundPaperService.FoundByKnowledge(params);
    }
    @PostMapping("FoundByTarget")
    public Result FoundByTarget(@RequestBody FoundByTargetVo params){

        return foundPaperService.FoundByTarget(params);
    }
   @PostMapping("FoundByRandom")
    public Result FoundByRandom(@RequestBody FoundByTargetVo params){
        return foundPaperService.FoundByRandom(params);
    }

    @PostMapping("FoundBySelf")
    public Result FoundBySelf(@RequestBody FoundBySelfVo params){
        return foundPaperService.FoundBySelf(params);
    }


    @GetMapping("getAllTargetList")
    public  Result getAllTargetList(@PathParam("courseId") Integer courseId,
                                    @PathParam("teacherId") Integer teacherId){
        return foundPaperService.getAllTargetList(courseId,teacherId);
    }
    @GetMapping("getAllDb")
    public  Result getAllDb(@PathParam("courseId") Integer courseId,
                                    @PathParam("teacherId") Integer teacherId){
        return foundPaperService.getAllDb(courseId,teacherId);
    }

}
