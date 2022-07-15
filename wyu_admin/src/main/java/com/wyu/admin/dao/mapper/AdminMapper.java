package com.wyu.admin.dao.mapper;/*
 *@interface: AdminMapper
 *AUTHOR lizhian
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyu.admin.dao.pojo.admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper extends BaseMapper<admin> {
}
