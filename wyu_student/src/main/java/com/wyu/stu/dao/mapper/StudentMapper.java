package com.wyu.stu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyu.stu.dao.pojo.student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<student> {
}
