package com.wyu.tea.vo;

import lombok.Data;

/**
 * @PackageName:com.wyu.tea.vo
 * @ClassName:DbTargetVo
 * @Description:
 * @author:Aan
 * @data 2022/2/25 0:35
 **/
@Data
public class DbTargetVo {
    private Integer dbId;
    private String dbType;
    private String dbContent;
    private String dbAnswer;
    private Integer targetId;
    private String tarContent;
    private Integer knoId;
    private String knoContent;
    private Integer knoCourse;
}
