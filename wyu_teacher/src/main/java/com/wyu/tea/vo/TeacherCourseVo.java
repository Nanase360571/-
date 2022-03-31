package com.wyu.tea.vo;

import com.wyu.common.dao.pojo.course;
import com.wyu.common.dao.pojo.knowledge;
import com.wyu.common.dao.pojo.target;
import lombok.Data;

import java.util.List;

/**
 * @PackageName:com.wyu.tea.vo.params
 * @ClassName:TeacherCourseVo
 * @Description:
 * @author:Aan
 * @data 2022/2/1 14:18
 **/
@Data
public class TeacherCourseVo {

    private List<course> courseList;

    private List<target> targetList;

    private  List<knowledge> knowledgeList;
}
