package com.wyu.admin.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @PackageName:com.wyu.stu.handle
 * @ClassName:LoginInterceptor
 * @Description:
 * @author:Aan
 * @data 2022/1/19 22:20
 **/
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    //在执行Controller方法（Handler）方法前执行
    /*
     *1. 需要判断 请求路径是否为HandlerMethod（Controller方法）
     *2. 判断token是否为空 如果为空 即未登录
     *3. 如果不为空 登录认证 loginService checkToken（验证redis中是否存在该token（即是否过期））
     *4. 如果认证成功 放行即可
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        log.info(request.getRequestURI());
        if(request.getRequestURI().equals("/loginStudent")||request.getRequestURI().equals("/loginTeacher")||request.getRequestURI().equals("/loginAdmin"))
        {
            log.info("登陆页面，不进行拦截");
            return true;
        }
        if(!(handler instanceof HandlerMethod))
        {
            log.info("访问静态资源");
            //Handler 可能是RequestResourceHandler
            //springboot 程序访问静态资源 默认去classpath下的static目录去查询
            return true;
        }

        String token = request.getHeader("Authorization");


        return true;
    }


}
