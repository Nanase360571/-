package com.wyu.tea.service;

import com.wyu.common.vo.Result;
import com.wyu.tea.dao.pojo.student;
import com.wyu.tea.vo.params.StudentParams;
import com.wyu.tea.vo.params.StudentToCourseParam;
import com.wyu.tea.vo.params.UpdateKnowledgeParam;

public interface CourseService {
    Result getTeacherCourse(String teacherId);

    Result getStudentList(StudentParams studentParams);

    Result removeStudent(StudentParams studentParams);

    Result addStudentToCourse(StudentToCourseParam studentToCourseParam);

    Result getKnowledge(StudentParams studentParams);

    Result updateKnowledge(UpdateKnowledgeParam updateKnowledgeParam);

    Result removeKnowledge(UpdateKnowledgeParam updateKnowledgeParam);

    Result addKnowledgeToCourse(UpdateKnowledgeParam updateKnowledgeParam);
}
