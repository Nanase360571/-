package com.wyu.stu.service.impl;

import com.alibaba.fastjson.JSON;
import com.wyu.common.util.JWTUtils;
import com.wyu.common.vo.Result;
import com.wyu.stu.dao.pojo.student;
import com.wyu.stu.service.LoginService;
import com.wyu.stu.service.StudentService;
import com.wyu.stu.vo.params.LoginParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @PackageName:com.wyu.stu.service.impl
 * @ClassName:LoginServiceImpl
 * @Description:
 * @author:Aan
 * @data 2022/1/19 23:23
 **/
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {



    @Autowired
    private StudentService studentService;
    @Autowired
    private RedisTemplate<String ,String> redisTemplate;
    @Override
    public Result loginStudent(LoginParams loginParams) {
        /*
         * 1. 检查参数合法性
         * 2. 根据用户名和密码去user表中查询，是否存在
         * 3. 如果不存在，登陆失败
         * 4. 如果存在，使用jwt 生成token，返回给前端
         * 5. token放入到redis中， redis  token：user信息 设置过期时间（登录认证时候，先验证token的字符串是否合法，去redis认证是否存在）
         *
         * */
        if(StringUtils.isBlank(loginParams.getAccount()) || StringUtils.isBlank(loginParams.getPassword())){
            return Result.fail(10003,"账号或者密码有误");
        }

        student student = studentService.queryStudent(loginParams);

        if(student == null){
            return Result.fail(10003,"账号或者密码有误");
        }
        String token = JWTUtils.createToken(loginParams.getAccount());
        redisTemplate.opsForValue().set("TOKEN"+token, JSON.toJSONString(student),1, TimeUnit.DAYS);
        log.info("存储到redis的token---------"+token);
        return Result.success(token);

    }

    @Override
    public Result updatePassword(LoginParams params) {

        return studentService.updatePassword(params);
    }

    @Override
    public Result getStudentMsg(LoginParams params) {
        student student = studentService.queryStudent(params);

        return Result.success(student);
    }


}
