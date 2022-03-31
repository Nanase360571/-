package com.wyu.tea.service;

import com.wyu.common.vo.Result;
import com.wyu.tea.vo.params.FoundByKnowledgeVo;
import com.wyu.tea.vo.params.FoundBySelfVo;
import com.wyu.tea.vo.params.FoundByTargetVo;

import javax.management.relation.RelationSupport;

public interface FoundPaperService {
    Result FoundByKnowledge(FoundByKnowledgeVo params);

    Result FoundByTarget(FoundByTargetVo params);

    Result getAllTargetList(Integer courseId, Integer teacherId);

    Result FoundByRandom(FoundByTargetVo params);

    Result getAllDb(Integer courseId, Integer teacherId);

    Result FoundBySelf(FoundBySelfVo params);
}
