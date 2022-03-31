package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:courseTargeTeacher
 * @Description:
 * @author:Aan
 * @data 2022/2/27 22:37
 **/

@Data
public class courseTargetTeacher {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer cttTeacher;
    private Integer cttTarget;
}
