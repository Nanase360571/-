package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:dbCourse
 * @Description:
 * @author:Aan
 * @data 2022/2/25 0:02
 **/
@Data
public class dbCourse {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer dcDb;
    private Integer dcCourse;
}
