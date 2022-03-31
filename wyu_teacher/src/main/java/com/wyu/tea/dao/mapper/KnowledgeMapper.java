package com.wyu.tea.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyu.common.dao.pojo.knowledge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KnowledgeMapper extends BaseMapper<knowledge> {
    int addKnowledge(knowledge knowledge);

    void insertBatchKnowledge(List<knowledge> newKnowledgeList);
}
