package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:courseKnowledge
 * @Description:
 * @author:Aan
 * @data 2022/1/31 17:00
 **/
@Data
public class courseKnowledgeTeacher {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private Integer ckCourse;
    private Integer ckKnowledge;
    private Integer ckTeacher;
}
