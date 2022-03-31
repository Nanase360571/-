package com.wyu.stu.controller;

import com.wyu.common.vo.Result;
import com.wyu.stu.service.LoginService;
import com.wyu.stu.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @PackageName:com.wyu.stu.controller
 * @ClassName:TestController
 * @Description:
 * @author:Aan
 * @data 2022/1/18 18:37
 **/
@RestController
@RequestMapping
public class LoginController {
    @Autowired
    private LoginService loginService;

    /*
    * 学生登录
    * */
    @PostMapping("/loginStudent")
    public Result login(@RequestBody LoginParams loginParams){
        return loginService.loginStudent(loginParams);
    }

    /*
    * 修改密码
    * */
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody LoginParams params){
        System.out.println(params.getPassword());
        return loginService.updatePassword(params);
    }

    /*
    * 获取学生信息，存储到前端的store
    * */
    @PostMapping("/getStudentMsg")
    public Result getStudentMsg(@RequestBody LoginParams loginParams){
        return loginService.getStudentMsg(loginParams);
    }

}

