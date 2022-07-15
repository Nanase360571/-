package com.wyu.tea.service;

import com.wyu.common.dao.pojo.db;
import com.wyu.common.vo.Result;
import com.wyu.tea.vo.AddDBVo;
import com.wyu.tea.vo.params.UpdateKnowledgeAndTarget;

import java.util.List;

public interface TeacherDBService {
    Result getCourseDB(Integer courseId,Integer teacherId);

    Result getTargetList(Integer courseId, Integer teacherId);

    Result getKnowledgeList(Integer courseId, Integer teacherId);

    Result updateKnowledgeAndTarget(UpdateKnowledgeAndTarget updateKnowledgeAndTarget);

    Result addBD(AddDBVo addDBVo);

    Result addPatchDb(List<db> dbs);

    Result getAllCourse(Integer teacherId);
}
