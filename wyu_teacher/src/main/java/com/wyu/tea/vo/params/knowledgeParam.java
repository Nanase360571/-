package com.wyu.tea.vo.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class knowledgeParam {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private String knoContent;
    private Integer knoCourse;
    private Integer proportion;
}