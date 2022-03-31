package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:classes
 * @Description:
 * @author:Aan
 * @data 2022/2/10 14:44
 **/
@Data
public class classes {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private String claNo;
}
