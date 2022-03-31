package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:paperDb
 * @Description:
 * @author:Aan
 * @data 2022/3/13 10:47
 **/
@Data
public class paperDb {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer pdPaper;
    private Integer pdDb;
    private Integer pdScore;
}
