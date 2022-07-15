package com.wyu.admin.service;/*
 *@interface: AdminTeacherService
 *AUTHOR lizhian
 */

import com.wyu.admin.dao.pojo.teacher;
import com.wyu.common.vo.Result;

public interface AdminTeacherService {
    Result addAdminTeacher(teacher teacher);
}
