package com.wyu.admin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.admin.dao.pojo.admin;
import com.wyu.admin.dao.mapper.AdminMapper;
import com.wyu.admin.service.LoginService;
import com.wyu.admin.vo.params.LoginParams;
import com.wyu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 *@CLASSNAME: loginServiceImpl
 *AUTHOR lizhian
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Result loginAdmin(LoginParams loginParams) {
        QueryWrapper<admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", loginParams.getAccount());
        admin admin = adminMapper.selectOne(queryWrapper);
        if (loginParams.getPassword().equals(admin.getPassword())) {
            return Result.success(200);
        }

        return Result.fail(10001, "账号或者密码错误");
    }

    @Override
    public Result getAdminMsg(LoginParams loginParams) {
        QueryWrapper<admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", loginParams.getAccount());
        admin admin = adminMapper.selectOne(queryWrapper);
        return Result.success(admin);
    }
}