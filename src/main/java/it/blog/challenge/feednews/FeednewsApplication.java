package it.blog.challenge.feednews;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class FeednewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeednewsApplication.class, args);
	}
	
	@Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("HackerNewsLookup-");
        executor.initialize();
        return executor;
    }


}
