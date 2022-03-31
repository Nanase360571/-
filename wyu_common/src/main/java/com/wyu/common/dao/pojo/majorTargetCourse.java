package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:courseTarget
 * @Description:
 * @author:Aan
 * @data 2022/1/31 17:02
 **/
@Data
public class majorTargetCourse {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer mtcCourse;
    private Integer mtcTarget;
    private Integer mtcMajor;
    private Integer proportion;

}
