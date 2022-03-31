package com.wyu.stu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wyu.common.util.JWTUtils;
import com.wyu.common.vo.Result;
import com.wyu.stu.dao.mapper.StudentMapper;
import com.wyu.stu.dao.pojo.student;
import com.wyu.stu.service.StudentService;
import com.wyu.stu.vo.params.LoginParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * @PackageName:com.wyu.stu.service.impl
 * @ClassName:StudentServiceImpl
 * @Description:
 * @author:Aan
 * @data 2022/1/18 18:50
 **/
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public student queryStudent(LoginParams loginParams) {

        QueryWrapper<student> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account",loginParams.getAccount())
                .eq("password",loginParams.getPassword());
        student student = studentMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public Result updatePassword(LoginParams params) {
        UpdateWrapper<student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("account",params.getAccount());
        student student=new student();
        BeanUtils.copyProperties(params,student);
        int update = studentMapper.update(student, updateWrapper);
        if(update == 0){
            return Result.fail(10004,"修改失败");
        }
        return Result.success(update);
    }
}
