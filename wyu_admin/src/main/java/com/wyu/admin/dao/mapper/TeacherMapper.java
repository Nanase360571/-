package com.wyu.admin.dao.mapper;/*
 *@interface: TeacherMapper
 *AUTHOR lizhian
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyu.admin.dao.pojo.teacher;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherMapper extends BaseMapper<teacher> {
}
