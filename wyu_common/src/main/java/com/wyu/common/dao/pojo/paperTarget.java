package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:paperTarget
 * @Description:
 * @author:Aan
 * @data 2022/3/13 9:35
 **/
@Data
public class paperTarget {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer ptPaper;
    private Integer ptTarget;
    private Integer ptProportion;
}
