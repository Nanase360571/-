package com.wyu.stu.vo.params;

import com.wyu.common.vo.dbVo;
import lombok.Data;

import java.util.ArrayList;

@Data
public class SubmitPaperParams {
    private Integer account;
    private Integer paperId;
    private Integer studentId;
    private ArrayList<dbVo> singleChoiceArrayList;
    private ArrayList<dbVo> multipleChoiceArrayList;
    private ArrayList<dbVo> tfArrayList;

}
