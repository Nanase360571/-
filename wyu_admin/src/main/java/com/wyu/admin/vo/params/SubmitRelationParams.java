package com.wyu.admin.vo.params;

import lombok.Data;

import java.util.List;

/*
 *@CLASSNAME: SubmitRelationParams
 *AUTHOR lizhian
 */
@Data
public class SubmitRelationParams {
    private Integer majorValue;
    private Integer courseValues;
    private List<Integer> teacherValue;

}
