package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:courseTeacherStudent
 * @Description:
 * @author:Aan
 * @data 2022/2/9 17:55
 **/
@Data
public class courseTeacherStudent {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private  Integer ctsCourse;
    private Integer ctsTeacher;
    private Integer ctsStudent;
}
