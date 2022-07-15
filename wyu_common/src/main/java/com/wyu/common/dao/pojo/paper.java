package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:paper
 * @Description:
 * @author:Aan
 * @data 2022/3/13 9:24
 **/
@Data
public class paper {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private String papStart;
    private String papEnd;
    private Integer papSingle;
    private Integer papMulti;
    private Integer papJudge;
    private Integer papTotal ;
    private String papName;
    private Integer papSingleSum;
    private Integer papMultiSum;
    private Integer papJudgeSum;
    private Integer papFound;
    private String papExamTime;

}
