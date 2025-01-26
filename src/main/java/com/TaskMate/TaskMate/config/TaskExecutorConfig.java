package com.TaskMate.TaskMate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutorConfig {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3); // Minimum number of threads
        executor.setMaxPoolSize(4); // Maximum number of threads
        executor.setQueueCapacity(5); // Queue capacity
        executor.setThreadNamePrefix("ReminderExecutor-");
        executor.initialize();
        return executor;
    }
}
