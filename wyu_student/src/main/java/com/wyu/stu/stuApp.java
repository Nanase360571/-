package com.wyu.stu;

import com.wyu.stu.handle.LoginInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @PackageName:com.wyu.stu
 * @ClassName:stuApp
 * @Description:
 * @author:Aan
 * @data 2022/1/18 17:53
 **/
@SpringBootApplication
public class stuApp {
    public static void main(String[] args) {
        SpringApplication.run(stuApp.class);
    }
}
