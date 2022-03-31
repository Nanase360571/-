package com.wyu.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.wyu.common.dao.pojo
 * @ClassName:dbKnowledgeTeacher
 * @Description:
 * @author:Aan
 * @data 2022/2/28 14:06
 **/
@Data
public class dbKnowledgeTeacher {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer dktDb;
    private Integer dktKnowledge;
    private Integer dktTeacher;

}
