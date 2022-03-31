package com.wyu.stu.dao.teacherMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyu.common.dao.pojo.classes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassesMapper extends BaseMapper<classes> {
    void addPatchClass(List<classes> claNoList);

    int insertByIgnore(classes classes);
}
