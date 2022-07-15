package com.wyu.tea.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyu.common.dao.pojo.classesCourse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassesCourseMapper extends BaseMapper<classesCourse> {
    void insertClasses(classesCourse classesCourse);
    List<classesCourse> getList();
}
