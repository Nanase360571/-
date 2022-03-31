package com.wyu.tea.vo.params;

import lombok.Data;

/**
 * @PackageName:com.wyu.tea.vo.params
 * @ClassName:updateKnowledgeParam
 * @Description:
 * @author:Aan
 * @data 2022/2/14 23:22
 **/
@Data
public class UpdateKnowledgeParam {
    private Integer id;
    private Integer courseId;
    private Integer teacherId;
    private String knoContent;
}
