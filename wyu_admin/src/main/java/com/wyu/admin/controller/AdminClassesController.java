package com.wyu.admin.controller;

import com.wyu.admin.service.AdminClassesService;
import com.wyu.common.dao.pojo.classes;
import com.wyu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *@CLASSNAME: AdminClassesController
 *AUTHOR lizhian
 */
@RestController
@RequestMapping
public class AdminClassesController {
    @Autowired
    private AdminClassesService adminClassesService;
    @PostMapping("addAdminClasses")
    public Result addAdminClasses(@RequestBody classes classes){
        return adminClassesService.addAdminClasses(classes);
    }
}
