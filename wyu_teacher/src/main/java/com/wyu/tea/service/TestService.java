package com.wyu.tea.service;

import com.wyu.common.dao.pojo.course;
import com.wyu.common.vo.Result;

public interface TestService {
    Result startTest(course course);

    Result endTest(course course);
}
