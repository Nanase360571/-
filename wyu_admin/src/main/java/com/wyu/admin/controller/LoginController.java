package com.wyu.admin.controller;

import com.wyu.admin.service.LoginService;
import com.wyu.admin.vo.params.LoginParams;
import com.wyu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *@CLASSNAME: LoginController
 *AUTHOR lizhian
 */
@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("loginAdmin")
    public Result loginAdmin(@RequestBody LoginParams loginParams){
        return loginService.loginAdmin(loginParams);
    }
    @PostMapping("getAdminMsg")
    public Result getAdminMsg(@RequestBody LoginParams loginParams){
        return loginService.getAdminMsg(loginParams);
    }

}
