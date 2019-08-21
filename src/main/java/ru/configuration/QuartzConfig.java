package ru.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import ru.configuration.quartz.AutoWiringSpringBeanJobFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {
    private final ApplicationContext applicationContext;

    @Value("${auto-diagnosis.quartz-property-file}")
    private String quartzPropertyFile;

    @Bean
    public SchedulerFactoryBean scheduler(){
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setConfigLocation(new FileSystemResource(quartzPropertyFile));
        schedulerFactory.setGlobalJobListeners(jobExecutionListener());

        return schedulerFactory;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new AutoWiringSpringBeanJobFactory(applicationContext);
    }

    @Bean
    ru.configuration.quartz.JobExecutionListener jobExecutionListener(){
        return new ru.configuration.quartz.JobExecutionListener();
    }

}
