package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:paperTeacherCourse
 * @Description:
 * @author:Aan
 * @data 2022/3/13 9:28
 **/
@Data
public class paperTeacherCourse {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer ptcTeacher;
    private Integer ptcPaper;
    private Integer ptcCourse;

}
