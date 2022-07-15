package com.wyu.tea.controller;

import com.wyu.common.dao.pojo.course;
import com.wyu.common.vo.Result;
import com.wyu.tea.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("startTest")
    public Result startTest(@PathParam("id") Integer id,@PathParam("couSubject") String couSubject,
                            @PathParam("couNumber") String couNumber){
        course course= new course();
        course.setIsTest(1);
        course.setCouNumber(couNumber);
        course.setCouSubject(couSubject);
        course.setId(id);
        return testService.startTest(course);
    }
    @GetMapping("endTest")
    public Result endTest(@PathParam("id") Integer id,@PathParam("couSubject") String couSubject,
                            @PathParam("couNumber") String couNumber){
        course course= new course();
        course.setIsTest(0);
        course.setCouNumber(couNumber);
        course.setCouSubject(couSubject);
        course.setId(id);
        return testService.endTest(course);
    }
}
