package com.wyu.tea.service.Impl;

import com.wyu.tea.dao.mapper.StudentMapper;
import com.wyu.tea.dao.pojo.student;
import com.wyu.tea.service.AddStudent;
import com.wyu.tea.service.WorkIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackageName:com.wyu.tea.service.Impl
 * @ClassName:AddStudentImpl
 * @Description:
 * @author:Aan
 * @data 2022/1/25 16:54
 **/
@Service
public class AddStudentImpl implements AddStudent {

    @Autowired
    private StudentMapper mapper;

    @Autowired
    private WorkIService workIService;
    /*
    * 批量添加学生
    *
    * */
    @Override
    public void addPatch(List<student> studentList) {
        mapper.addPatchStudent(studentList);
    }
}
