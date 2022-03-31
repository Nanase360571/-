package com.wyu.tea.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyu.tea.dao.mapper.StudentMapper;
import com.wyu.tea.dao.pojo.student;
import com.wyu.tea.service.WorkIService;
import org.springframework.stereotype.Service;

/**
 * @PackageName:com.wyu.tea.service.Impl
 * @ClassName:WorkServiceImpl
 * @Description:
 * @author:Aan
 * @data 2022/1/25 23:05
 **/
@Service
public class WorkServiceImpl extends ServiceImpl<StudentMapper, student> implements WorkIService {

}
