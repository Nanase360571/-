package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:major
 * @Description:
 * @author:Aan
 * @data 2022/2/15 18:23
 **/
@Data
public class major {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private String major;
}
