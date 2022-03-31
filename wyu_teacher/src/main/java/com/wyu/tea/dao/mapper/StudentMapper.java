package com.wyu.tea.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyu.tea.dao.pojo.student;
import com.wyu.tea.vo.params.StudentFromExcel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<student> {
    void addPatchStudent(List<student> studentList);
    List<student> selectByIds(List<Integer> list);

    int insertByIgnore(student student);
}
