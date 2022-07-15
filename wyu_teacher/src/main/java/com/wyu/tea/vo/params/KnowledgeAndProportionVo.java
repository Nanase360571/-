package com.wyu.tea.vo.params;

import com.wyu.common.dao.pojo.knowledge;
import lombok.Data;

@Data
public class KnowledgeAndProportionVo{
    private com.wyu.common.dao.pojo.knowledge knowledge;
    private Integer proportion;
    private Double score;
    private Double singleEve;
    private Double multiEve;
    private Double judgeEve;
}