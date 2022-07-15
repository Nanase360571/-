package com.wyu.admin.service.Impl;

import com.wyu.admin.dao.mapper.ClassesMapper;
import com.wyu.admin.service.AdminClassesService;
import com.wyu.common.dao.pojo.classes;
import com.wyu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 *@CLASSNAME: AdminClassesServiceImpl
 *AUTHOR lizhian
 */
@Service
public class AdminClassesServiceImpl implements AdminClassesService {
    @Autowired
    public ClassesMapper classesMapper;
    @Override
    public Result addAdminClasses(classes classes) {
        try {
            return Result.success(classesMapper.insert(classes));
        }catch (Exception e)
        {
            return Result.fail(10001,"添加失败");
        }

    }
}
