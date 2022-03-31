package com.wyu.tea.vo.params;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @PackageName:com.wyu.tea.vo.params
 * @ClassName:UpdateKnowledgeAndTarget
 * @Description:
 * @author:Aan
 * @data 2022/3/3 14:44
 **/
@Data
public class UpdateKnowledgeAndTarget {
    private Integer courseId;
    private Integer knoId;
    private Integer targetId;
    private Integer teacherId;
    private Integer dbId;
}
