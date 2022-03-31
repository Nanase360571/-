package com.wyu.tea.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyu.common.dao.pojo.paper;
import com.wyu.common.dao.pojo.paperTeacherCourse;
import com.wyu.common.vo.Result;
import com.wyu.tea.dao.mapper.PaperMapper;
import com.wyu.tea.dao.mapper.PaperTeacherCourseMapper;
import com.wyu.tea.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @PackageName:com.wyu.tea.service.Impl
 * @ClassName:PaperServiceImpl
 * @Description:
 * @author:Aan
 * @data 2022/3/22 10:36
 **/

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private PaperTeacherCourseMapper paperTeacherCourseMapper;



    @Override
    public Result getAllPaperList(Integer teacherId) {
        QueryWrapper<paperTeacherCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ptc_teacher",teacherId);
        List<paperTeacherCourse> paperTeacherCourses = paperTeacherCourseMapper.selectList(queryWrapper);
        if(paperTeacherCourses.size() == 0)
        {
            return Result.fail(10003,"没有试卷");
        }
        List<Integer> collect = paperTeacherCourses.stream().map(paperTeacherCourse::getPtcPaper).collect(Collectors.toList());
        List<paper> papers = paperMapper.selectBatchIds(collect);
        return Result.success(papers);
    }
}
