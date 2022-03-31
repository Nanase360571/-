package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:courseTeacher
 * @Description:
 * @author:Aan
 * @data 2022/1/31 17:04
 **/
@Data
public class courseTeacherMajor {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer ctCourse;
    private Integer ctTeacher;
    private Integer ctMajor;

}
