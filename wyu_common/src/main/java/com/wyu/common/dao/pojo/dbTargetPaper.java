package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:dbTargetPaper
 * @Description:
 * @author:Aan
 * @data 2022/3/19 10:37
 **/
@Data
public class dbTargetPaper {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer dtpDb;
    private Integer dtpTarget;
    private Integer dtpPaper;
}
