package com.wyu.stu.dao.teacherMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyu.common.dao.pojo.majorTargetCourse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorTargetCourseMapper extends BaseMapper<majorTargetCourse> {
    void insertBatchExist(List<majorTargetCourse> majorTargetCoursesList);

    void insertBatchNoExist(List<majorTargetCourse> noExistMajorTargetCoursesList);
}
