package com.wyu.tea.vo.params;

/*
 *@CLASSNAME: DbResultVo
 *AUTHOR lizhian
 */

import lombok.Data;

@Data
public class DbResultVo {
    private Integer dbId;
    private Double score;
    private Integer relevanceId;
    private Integer dbType;


}
