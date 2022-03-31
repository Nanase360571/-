package com.wyu.tea.controller;

import com.wyu.common.vo.Result;
import com.wyu.tea.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
