package com.wyu.tea.vo.params;

import lombok.Data;

/**
 * @PackageName:com.wyu.tea.vo.params
 * @ClassName:StudentToCourseParam
 * @Description:
 * @author:Aan
 * @data 2022/2/10 22:11
 **/
@Data
public class StudentToCourseParam {
    private Integer teacherId;
    private String name;
    private String account;
    private String claNo;
    private Integer courseId;
}
