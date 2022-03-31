package com.wyu.tea.service;

import com.wyu.common.vo.Result;
import com.wyu.tea.vo.params.LoginParams;

public interface LoginService {
    Result loginTeacher(LoginParams loginParams);

    Result getTeacherMsg(LoginParams loginParams);

    Result updatePassword(LoginParams loginParams);
}
