package com.wyu.stu.service;

import com.wyu.common.vo.Result;

public interface STestService {
    Result getCourseTest(Integer studentId);

    Result randomTest(Integer id);
}
