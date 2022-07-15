package com.wyu.tea.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.common.dao.pojo.course;
import com.wyu.common.vo.Result;
import com.wyu.tea.dao.mapper.CourseMapper;
import com.wyu.tea.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private CourseMapper courseMapper;
    @Override
    @Transactional
    public Result startTest(course course) {
        QueryWrapper<course> queryWrapper = new QueryWrapper<course>();
        queryWrapper.eq("id",course.getId());
        courseMapper.update(course,queryWrapper);
        return Result.success(200);
    }

    @Override
    public Result endTest(course course) {
        QueryWrapper<course> queryWrapper = new QueryWrapper<course>();
        queryWrapper.eq("id",course.getId());
        courseMapper.update(course,queryWrapper);
        return Result.success(200);
    }
}
