package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:classesCourse
 * @Description:
 * @author:Aan
 * @data 2022/4/18 22:41
 **/
@Data
public class classesCourse {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer ccCourse;
    private Integer ccClass;
    private Integer ccTeacher;
}
