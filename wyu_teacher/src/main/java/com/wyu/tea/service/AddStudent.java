package com.wyu.tea.service;

import com.wyu.tea.dao.pojo.student;
import com.wyu.tea.vo.params.StudentFromExcel;

import java.util.List;

public interface AddStudent {
    void addPatch(List<student> studentFromExcels);
}
