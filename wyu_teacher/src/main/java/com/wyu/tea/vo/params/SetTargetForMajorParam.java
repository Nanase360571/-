package com.wyu.tea.vo.params;

import com.wyu.common.dao.pojo.target;
import com.wyu.tea.vo.TargetVo;
import lombok.Data;

import java.util.List;

/**
 * @PackageName:com.wyu.tea.vo.params
 * @ClassName:SetTargetForMajorVo
 * @Description:
 * @author:Aan
 * @data 2022/2/17 11:45
 **/
@Data
public class SetTargetForMajorParam {
    private Integer majorId;
    private Integer courseId;
    private List<TargetVo> targetList;
}
