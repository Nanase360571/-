package com.wyu.tea.service;

import com.wyu.common.vo.Result;
import com.wyu.tea.dao.pojo.teacher;
import com.wyu.tea.vo.params.LoginParams;
import org.springframework.stereotype.Service;

/**
 * @PackageName:com.wyu.tea.service
 * @ClassName:TeacherService
 * @Description:
 * @author:Aan
 * @data 2022/1/18 20:46
 **/
public interface TeacherService {
     teacher queryTeacher(LoginParams loginParams);

    Result updatePassword(LoginParams loginParams);
}
