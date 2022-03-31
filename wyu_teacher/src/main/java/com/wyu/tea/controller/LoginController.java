package com.wyu.tea.controller;

import com.wyu.common.vo.Result;
import com.wyu.tea.service.LoginService;
import com.wyu.tea.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PackageName:com.wyu.tea.controller
 * @ClassName:LoginController
 * @Description:
 * @author:Aan
 * @data 2022/1/24 15:22
 **/
@RestController
@RequestMapping
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("/loginTeacher")
    public Result loginTeacher(@RequestBody LoginParams loginParams){
        return loginService.loginTeacher(loginParams);
    }

    @PostMapping("/getTeacherMsg")
    public Result getTeacherMsg(@RequestBody LoginParams loginParams){
        return loginService.getTeacherMsg(loginParams);
    }
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody LoginParams loginParams){
        return loginService.updatePassword(loginParams);
    }
}
