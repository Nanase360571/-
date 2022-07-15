package com.wyu.tea.vo.params;

/*
 *@CLASSNAME: PaperResultVo
 *AUTHOR lizhian
 */

import com.wyu.common.dao.pojo.knowledge;
import com.wyu.tea.dao.pojo.student;
import com.wyu.tea.vo.StudentVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KnowledgePaperResultVo {
    /**
     * 学生信息
     * */
    private StudentVo student;
    /**
     * 每道题的答题情况
     * */
    List<DbResultVo> singleDbResultVos = new ArrayList<>();
    List<DbResultVo> multiDbResultVos = new ArrayList<>();
    List<DbResultVo> judgeDbResultVos = new ArrayList<>();
    /**
     * 各个模块分数
     * */
    private Double singleSumPoint;
    private Double multiSumPoint;
    private Double judgeSumPoint;
    /**
     * 知识点或者课程目标模块分数
     * */
    private Double judgeRelevanceSumPoint;
    private Double singleRelevanceSumPoint;
    private Double multiRelevanceSumPoint;
    /**
     * 该名学生总得分
     * */
    private Double sumPoint;
    /**
     * 知识点或者课程目标总得分
     * */
    private Double RelevanceSumPoint;/**
    /**
     * 所对应的知识点
     * */
    List<KnowledgeAndProportionVo> knowledgeAndProportions = new ArrayList<>();
    /**
     * 单选，多选，判断题量
     * */
    private Integer single;
    private Integer multi;
    private Integer judge;
}

