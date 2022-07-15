package com.wyu.tea.vo.params;

import com.wyu.common.dao.pojo.target;
import lombok.Data;

/**
 * @PackageName:com.wyu.tea.vo.params
 * @ClassName:TargetAndProportionVo
 * @Description:
 * @author:Aan
 * @data 2022/5/4 23:31
 **/
@Data
public class TargetAndProportionVo {
    private target target;
    private Integer proportion;
    private Double score;
    private Double singleEve;
    private Double multiEve;
    private Double judgeEve;}
