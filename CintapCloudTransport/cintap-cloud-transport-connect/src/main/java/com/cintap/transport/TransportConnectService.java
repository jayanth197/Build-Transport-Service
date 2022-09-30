package com.cintap.transport;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableAutoConfiguration
public class TransportConnectService extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(TransportConnectService.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TransportConnectService.class);
	}
	
	@Bean(name = "messageProcessHandlerExecutor")
    public ThreadPoolTaskExecutor getMessageProcessHandlerExecutor(){
        return createThreadPoolTaskExecutor(10, 30, "Message-Process-Handler-transport-");
    }
	
	@Bean(name = "consumerTaskExecutor")
    public ThreadPoolTaskExecutor createThreadPoolTaskExecutor(int minPoolSize, int maxPoolSize, String threadPrefixName){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix(threadPrefixName);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    } 
}
