package com.wyu.stu.service;

import com.wyu.common.vo.Result;
import com.wyu.stu.vo.params.LoginParams;

public interface LoginService {
    Result loginStudent(LoginParams loginParams);

    Result updatePassword(LoginParams params);

    Result getStudentMsg(LoginParams params);

}
