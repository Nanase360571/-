package com.wyu.admin.service;

import com.wyu.admin.vo.params.LoginParams;
import com.wyu.common.vo.Result;

/*
 *@CLASSNAME: loginServiceImpl
 *AUTHOR lizhian
 */
public interface LoginService {
    Result loginAdmin(LoginParams loginParams);

    Result getAdminMsg(LoginParams loginParams);
}
