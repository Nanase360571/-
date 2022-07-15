package com.wyu.admin.controller;

import com.wyu.admin.service.AdminMajorService;
import com.wyu.common.dao.pojo.major;
import com.wyu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *@CLASSNAME: AdminMajorController
 *AUTHOR lizhian
 */
@RestController
@RequestMapping
public class AdminMajorController {
    @Autowired
    private AdminMajorService adminMajorService;
    @PostMapping("addAdminMajor")
    public Result addAdminMajor(@RequestBody major major){
        return adminMajorService.addAdminMajor(major);
    }
}
