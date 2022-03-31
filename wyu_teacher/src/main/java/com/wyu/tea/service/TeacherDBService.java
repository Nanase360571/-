package com.wyu.tea.service;

import com.wyu.common.vo.Result;
import com.wyu.tea.vo.params.UpdateKnowledgeAndTarget;

public interface TeacherDBService {
    Result getCourseDB(Integer courseId,Integer teacherId);

    Result getTargetList(Integer courseId, Integer teacherId);

    Result getKnowledgeList(Integer courseId, Integer teacherId);

    Result updateKnowledgeAndTarget(UpdateKnowledgeAndTarget updateKnowledgeAndTarget);
}
