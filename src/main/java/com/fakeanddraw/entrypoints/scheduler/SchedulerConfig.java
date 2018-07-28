package com.fakeanddraw.entrypoints.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.fakeanddraw.entrypoints.scheduler")
public class SchedulerConfig {

  @Value("${scheduler.pool.size}")
  private Integer schedulerPoolSize;

  @Bean
  public Scheduler threadPoolTaskScheduler() {
    Scheduler threadPoolTaskScheduler = new Scheduler();
    threadPoolTaskScheduler.setPoolSize(schedulerPoolSize);
    threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
    return threadPoolTaskScheduler;
  }
}
