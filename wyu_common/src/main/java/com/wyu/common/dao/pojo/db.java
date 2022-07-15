package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:db
 * @Description:
 * @author:Aan
 * @data 2022/1/18 23:15
 **/
@Data
public class db {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer dbType;
    private String dbContent;
    private String dbAnswer;
    private String dbAnswerText;
    private Integer dbCourse;

}
