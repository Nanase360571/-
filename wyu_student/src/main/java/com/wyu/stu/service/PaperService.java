package com.wyu.stu.service;

import com.wyu.common.vo.Result;

public interface PaperService {

    Result getPaperSummary(Integer id);

    Result getPaperDb(Integer studentId, Integer paperId);
}
