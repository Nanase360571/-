package com.wyu.stu.controller;

import com.wyu.common.vo.Result;
import com.wyu.stu.service.STestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping
public class STestController {
    @Autowired
    private STestService sTestService;
    @GetMapping("getCourseTest")
    public Result getCourseTest(@PathParam("studentId")Integer studentId){

            return sTestService.getCourseTest(studentId);
    }
    @GetMapping("randomTest")
    public Result randomTest(@PathParam("id")Integer id){

            return sTestService.randomTest(id);
    }
}
