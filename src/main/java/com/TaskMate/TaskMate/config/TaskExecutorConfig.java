package com.TaskMate.TaskMate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
public class TaskExecutorConfig {

    @Bean
    public ThreadPoolTaskExecutor customTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Set core pool size
        executor.setMaxPoolSize(10); // Set max pool size
        executor.setQueueCapacity(25); // Set queue capacity
        executor.setThreadNamePrefix("ReminderExecutor-"); // Set thread name prefix
        executor.initialize();
        return executor;
    }
}
