package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 *@CLASSNAME: studentPaper
 *AUTHOR lizhian
 */
@Data
public class studentPaper {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer spStudent;
    private Integer spPaper;
    private Integer status;
}
