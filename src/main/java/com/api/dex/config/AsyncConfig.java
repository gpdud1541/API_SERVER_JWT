package com.api.dex.config;

import com.api.dex.handler.AsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    private static int TASK_CORE_POOL_SIZE = 2;
    private static int TASK_MAX_POOL_SIZE = 4;
    private static int TASK_QUEUE_CAPACITY = 10;
    private final String EXECUTOR_BEAN_NAME = "mailExecutor";

    @Resource(name="mailExecutor")
    private ThreadPoolTaskExecutor mailExecutor;

    @Bean(name="mailExecutor")
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(TASK_CORE_POOL_SIZE);
        executor.setMaxPoolSize(TASK_MAX_POOL_SIZE);
        executor.setQueueCapacity(TASK_QUEUE_CAPACITY);
        executor.setBeanName(EXECUTOR_BEAN_NAME);
        executor.setWaitForTasksToCompleteOnShutdown(false);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    /*
     * task 생성전에 pool이 찼는지를 체크
     */
    public boolean checkSampleTaskExecute() {
        boolean result = true;

        if(mailExecutor.getActiveCount() >= (TASK_MAX_POOL_SIZE + TASK_QUEUE_CAPACITY)) {
            result = false;
        }
        return result;
    }
}
