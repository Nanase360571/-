package com.wyu.tea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {
    @Bean("taskExecutor")
    public Executor asyncServiceExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor=new ThreadPoolTaskExecutor();
        //设置核心线程数
        threadPoolTaskExecutor.setCorePoolSize(5);
        //设置最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(20);
        //设置队列大小
        threadPoolTaskExecutor.setQueueCapacity(Integer.MAX_VALUE);
        //设置默认线程名称
        threadPoolTaskExecutor.setThreadNamePrefix("Anan");
        //等待所有任务都结束在关闭线程池
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}