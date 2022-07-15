package com.wyu.admin.service;/*
 *@interface: AdminMajorTeacherCourseService
 *AUTHOR lizhian
 */

import com.wyu.admin.vo.params.SubmitRelationParams;
import com.wyu.common.vo.Result;

/**
 * @author ly-lizhian
 */
public interface AdminMajorTeacherCourseService {
    Result getAllMajor();

    Result getAllClasses();

    Result getAllTeacher();

    Result submitRelation(SubmitRelationParams params);

    Result getAllCourse();

}
