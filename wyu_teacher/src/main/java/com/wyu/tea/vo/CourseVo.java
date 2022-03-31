package com.wyu.tea.vo;

import com.wyu.common.dao.pojo.course;
import com.wyu.common.dao.pojo.knowledge;
import com.wyu.common.dao.pojo.target;
import lombok.Data;

import java.util.List;

/**
 * @PackageName:com.wyu.tea.vo
 * @ClassName:CourseVo
 * @Description:
 * @author:Aan
 * @data 2022/2/4 0:06
 **/
@Data
public class CourseVo {

    private course course;

    private List<target> targetList;

    private  List<knowledge> knowledgeList;

}
