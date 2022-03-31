package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:majorClasses
 * @Description:
 * @author:Aan
 * @data 2022/2/15 20:31
 **/
@Data
public class majorClasses {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer mcMajor;
    private Integer mcClasses;
}
