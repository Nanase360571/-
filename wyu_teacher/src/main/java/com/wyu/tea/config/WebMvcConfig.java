package com.wyu.tea.config;

import com.wyu.tea.handle.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @PackageName:com.mszlu.blog.config
 * @ClassName:WebMvcConfig
 * @Description:
 * @author:Aan
 * @data 2021/8/1 17:51
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置
        registry.addMapping("/**").allowedOrigins("*");


    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor);
//                .addPathPatterns("/test")
//                .addPathPatterns("/comments/create/change")
//                .addPathPatterns("/articles/publish");
    }


}
