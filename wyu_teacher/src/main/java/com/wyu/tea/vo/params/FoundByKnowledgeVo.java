package com.wyu.tea.vo.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wyu.common.dao.pojo.knowledge;
import lombok.Data;

import java.util.List;

/**
 * @PackageName:com.wyu.tea.vo.params
 * @ClassName:FoundByKnowledgeVo
 * @Description:
 * @author:Aan
 * @data 2022/3/6 23:46
 **/
@Data
public class FoundByKnowledgeVo {
    @TableId(value="id",type = IdType.AUTO)
    private Integer teacherId;
    private Integer courseId;
    private String paperName;
    private Integer paperSum;
    private String startTime;
    private String endTime;
    private Integer multiNumber;
    private Integer singleNumber;
    private Integer judgeNumber;
    private Integer singleSum;
    private Integer judgeSum;
    private Integer multiSum;
    private List<knowledgeParam> knowledgeList;
}
