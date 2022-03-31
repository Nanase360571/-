package com.wyu.stu.controller;

import com.wyu.common.vo.Result;
import com.wyu.stu.service.PaperService;
import com.wyu.stu.vo.params.SubmitPaperParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * @PackageName:com.wyu.stu.controller
 * @ClassName:PaperController
 * @Description:
 * @author:Aan
 * @data 2022/1/21 23:24
 **/
@RestController
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private PaperService service;

    @GetMapping("/getPaperSummary")
    public Result getPaperSummary(@PathParam("studentId") Integer studentId){
        return  service.getPaperSummary(studentId);
    }
    @GetMapping("/getPaperDb")
    public Result getPaperDb(@PathParam("studentId") Integer studentId,
                             @PathParam("paperId") Integer paperId){
        return  service.getPaperDb(studentId,paperId);
    }
    @PostMapping("/submitPaper")
    public Result submitPaper(@RequestBody SubmitPaperParams submitPaperParams){
        System.out.println(submitPaperParams);
        return  null;
    }
}
