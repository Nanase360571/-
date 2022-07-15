package com.wyu.stu.service;

import com.wyu.common.vo.Result;
import com.wyu.stu.vo.params.SubmitPaperParams;

public interface PaperService {

    Result getPaperSummary(Integer id);

    Result getPaperDb(Integer studentId, Integer paperId);

    Result submitPaper(SubmitPaperParams submitPaperParams);
}
