package com.wyu.admin.service;/*
 *@interface: AdminCourseService
 *AUTHOR lizhian
 */

import com.wyu.common.dao.pojo.course;
import com.wyu.common.vo.Result;

public interface AdminCourseService {
    Result addAdminCourse(course course);
}
