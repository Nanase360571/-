package com.wyu.tea.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyu.common.dao.pojo.courseTeacherStudent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseTeacherStudentMapper extends BaseMapper<courseTeacherStudent> {
    void addPatchStudentToCourse(List<courseTeacherStudent> list);

    List<courseTeacherStudent> getPatchcourseTeacherStudents(List<courseTeacherStudent> courseTeacherStudents);

    List<courseTeacherStudent> getCourseList(Integer teacherId);
}
