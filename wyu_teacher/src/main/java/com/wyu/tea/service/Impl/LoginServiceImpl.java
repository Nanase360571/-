package com.wyu.tea.service.Impl;

import com.alibaba.fastjson.JSON;
import com.wyu.common.util.JWTUtils;
import com.wyu.common.vo.Result;
import com.wyu.tea.dao.pojo.teacher;
import com.wyu.tea.service.LoginService;
import com.wyu.tea.service.TeacherService;
import com.wyu.tea.vo.params.LoginParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @PackageName:com.wyu.tea.service.Impl
 * @ClassName:LoginServiceImpl
 * @Description:
 * @author:Aan
 * @data 2022/1/24 15:23
 **/
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    @Autowired
    private TeacherService teacherService;
    @Override
    public Result loginTeacher(LoginParams loginParams) {
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

        teacher teacher = teacherService.queryTeacher(loginParams);
        if(teacher == null || !teacher.getPassword().equals(loginParams.getPassword() )){
            return Result.fail(10003,"账号或者密码有误");
        }
        String token = JWTUtils.createToken(teacher.getAccount());
        redisTemplate.opsForValue().set("TOKEN"+token, JSON.toJSONString(teacher),1, TimeUnit.DAYS);
        log.info("存储到redis的token---------"+token);
        return Result.success(token);

    }

    @Override
    public Result getTeacherMsg(LoginParams loginParams) {
        return Result.success(teacherService.queryTeacher(loginParams));
    }

    @Override
    public Result updatePassword(LoginParams loginParams) {
        return teacherService.updatePassword(loginParams);

    }
}
