package com.wyu.admin.service.Impl;

import com.wyu.admin.dao.mapper.MajorMapper;
import com.wyu.admin.service.AdminMajorService;
import com.wyu.common.dao.pojo.major;
import com.wyu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 *@CLASSNAME: AdminMajorServiceImpl
 *AUTHOR lizhian
 */
@Service
public class AdminMajorServiceImpl implements AdminMajorService {
    @Autowired
    private MajorMapper mapper;
    @Override
    public Result addAdminMajor(major major) {
        try {
            return Result.success(mapper.insert(major));
        }catch (Exception e){
            return Result.fail(10001,"该专业已存在");
        }

    }
}
