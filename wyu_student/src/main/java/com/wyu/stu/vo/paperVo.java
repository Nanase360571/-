package com.wyu.stu.vo;

import com.wyu.common.vo.dbVoList;
import lombok.Data;

import java.util.Date;

@Data
public class paperVo {
    private dbVoList dbVoList;
    private Integer singlePoint;
    private Integer multiPoint;
    private Integer judgePoint;
    private String startTime;
    private String endTime;
    private String fillTime;

}
