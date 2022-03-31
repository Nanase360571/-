package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:dbTarget
 * @Description:
 * @author:Aan
 * @data 2022/2/25 0:26
 **/
@Data
public class dbTargetTeacher {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer dtDb;
    private Integer dtTarget;
    private Integer dtTeacher;
}
