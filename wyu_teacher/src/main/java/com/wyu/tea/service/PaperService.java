package com.wyu.tea.service;

import com.wyu.common.vo.Result;
import com.wyu.tea.vo.params.AnalyseByKnowledgeVo;

public interface PaperService {
    Result getAllPaperList(Integer teacherId);

    Result getAllClasses(Integer teacherId, Integer paperId);

    Result analyseByKnowledge(AnalyseByKnowledgeVo params);

    Result analyseByTarget(AnalyseByKnowledgeVo params);

    Result analyseByRandom(AnalyseByKnowledgeVo params);

    Result analyseBySelf(AnalyseByKnowledgeVo params);
}
