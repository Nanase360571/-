package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:paperKnowledge
 * @Description:
 * @author:Aan
 * @data 2022/3/13 23:25
 **/
@Data
public class paperKnowledge {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer pkPaper;
    private Integer pkKnowledge;
    private Integer pkProportion;
}
