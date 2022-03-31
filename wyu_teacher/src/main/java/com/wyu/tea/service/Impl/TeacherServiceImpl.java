package com.wyu.tea.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wyu.common.vo.Result;
import com.wyu.tea.dao.mapper.TeacherMapper;
import com.wyu.tea.dao.pojo.teacher;
import com.wyu.tea.service.TeacherService;
import com.wyu.tea.vo.params.LoginParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @PackageName:com.wyu.tea.service.Impl
 * @ClassName:TeacherServiceImpl
 * @Description:
 * @author:Aan
 * @data 2022/1/18 20:47
 **/
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;


    @Override
    public teacher queryTeacher(LoginParams loginParams) {
        QueryWrapper<teacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account",loginParams.getAccount());
        teacher teacher = teacherMapper.selectOne(queryWrapper);

        return teacher;
    }

    @Override
    public Result updatePassword(LoginParams loginParams) {
        UpdateWrapper<teacher> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("account",loginParams.getAccount());
        teacher teacher=new teacher();
        BeanUtils.copyProperties(loginParams,teacher);
        int update = teacherMapper.update(teacher, updateWrapper);
        if(update == 0){
            return Result.fail(10004,"修改失败");
        }
        return Result.success(update);
    }


}
