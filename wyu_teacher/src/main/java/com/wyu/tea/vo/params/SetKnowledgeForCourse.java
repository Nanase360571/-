package com.wyu.tea.vo.params;

import com.wyu.common.dao.pojo.knowledge;
import com.wyu.common.dao.pojo.target;
import lombok.Data;

import java.util.List;

/**
 * @PackageName:com.wyu.tea.vo.params
 * @ClassName:SetKnowledgeForCourse
 * @Description:
 * @author:Aan
 * @data 2022/3/5 14:58
 **/
@Data
public class SetKnowledgeForCourse {
    private Integer teacherId;
    private Integer courseId;
    private List<knowledge> knowledgeList;

}
