package com.wyu.stu.service;

import com.wyu.common.vo.Result;
import com.wyu.stu.dao.pojo.student;
import com.wyu.stu.vo.params.LoginParams;

public interface StudentService {
    student queryStudent(LoginParams loginParams);

    Result updatePassword(LoginParams params);
}
