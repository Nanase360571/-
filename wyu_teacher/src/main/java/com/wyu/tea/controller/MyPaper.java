package com.wyu.tea.controller;

import com.wyu.common.vo.Result;
import com.wyu.tea.service.PaperService;
import com.wyu.tea.vo.params.AnalyseByKnowledgeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * @PackageName:com.wyu.tea.controller
 * @ClassName:MyPaper
 * @Description:
 * @author:Aan
 * @data 2022/3/22 10:34
 **/
@RestController
@RequestMapping
public class MyPaper {

    @Autowired
    private PaperService paperService;

    @GetMapping("getAllPaperList")
    public Result getAllPaperList(@PathParam("teacherId") Integer teacherId){

        return paperService.getAllPaperList(teacherId);
    }

    @GetMapping("getAllClasses")
    public Result getAllClasses(@PathParam("teacherId") Integer teacherId,
                                @PathParam("paperId") Integer paperId){

        return paperService.getAllClasses(teacherId,paperId);
    }
    @PostMapping("analyseByKnowledge")
    public Result analyseByKnowledge(@RequestBody AnalyseByKnowledgeVo params){

        return paperService.analyseByKnowledge(params);
    }

    @PostMapping("analyseByTarget")
    public Result analyseByTarget(@RequestBody AnalyseByKnowledgeVo params){

        return paperService.analyseByTarget(params);
    }
    @PostMapping("analyseByRandom")
    public Result analyseByRandom(@RequestBody AnalyseByKnowledgeVo params){

        return paperService.analyseByRandom(params);
    }

    @PostMapping("analyseBySelf")
    public Result analyseBySelf(@RequestBody AnalyseByKnowledgeVo params){

        return paperService.analyseBySelf(params);
    }



}
