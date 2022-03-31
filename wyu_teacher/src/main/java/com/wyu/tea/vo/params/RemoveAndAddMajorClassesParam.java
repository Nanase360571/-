package com.wyu.tea.vo.params;

import lombok.Data;

/**
 * @PackageName:com.wyu.tea.vo.params
 * @ClassName:RemoveMajorClassesParam
 * @Description:
 * @author:Aan
 * @data 2022/2/15 21:12
 **/
@Data
public class RemoveAndAddMajorClassesParam {
    private Integer classesId;
    private Integer majorId;
    private Integer courseId;
}
