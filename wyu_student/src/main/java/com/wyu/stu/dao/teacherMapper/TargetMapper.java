package com.wyu.stu.dao.teacherMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyu.common.dao.pojo.target;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TargetMapper extends BaseMapper<target> {
    void insertBatch(List<target> existTargetList);

    void insertBatchNotExist(List<target> noExistTargetList);
}
