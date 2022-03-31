package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:paperStudentDb
 * @Description:
 * @author:Aan
 * @data 2022/3/13 9:30
 **/
@Data
public class paperStudentDb {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer psdStudent;
    private Integer psdPaper;
    private Integer psdDb;
    private Integer psdScore;
    private Integer psdAnswer;
}
