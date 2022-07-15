package com.wyu.tea.service;

import com.wyu.common.vo.Result;
import com.wyu.tea.vo.params.RemoveAndAddMajorClassesParam;
import com.wyu.tea.vo.params.SetKnowledgeForCourse;
import com.wyu.tea.vo.params.SetTargetForMajorParam;

public interface ClassesMajorTargetService {
    Result getMajorList();

    Result getMajorClasses(Integer majorId);

    Result removeMajorClasses(RemoveAndAddMajorClassesParam param);

    Result addClassesToMajor(RemoveAndAddMajorClassesParam param);

    Result getLeftClasses(Integer majorId);

    Result getCourseList(Integer teacherId,Integer majorId);

    Result getTargetList(RemoveAndAddMajorClassesParam param);

    Result setTargetForMajor(SetTargetForMajorParam param);

    Result getKnowledgeList(Integer courseId, Integer teacherId);

    Result setKnowledgeForCourse(SetKnowledgeForCourse param);
}
