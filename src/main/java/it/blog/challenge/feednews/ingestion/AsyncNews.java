package it.blog.challenge.feednews.ingestion;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import it.blog.challenge.feednews.bean.hackernews.Hacker;

@Service
public class AsyncNews {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final RestTemplate restTemplate;

    public AsyncNews(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
	@Async
    public CompletableFuture<Hacker> getNewsDetail(String hackerDetailUrl, String item) throws InterruptedException {
      
		log.debug(String.format("Start GetNews id: %s", item));
		
        String url = String.format(hackerDetailUrl, item);
        Hacker hacker = restTemplate.getForObject(url, Hacker.class);
        
        log.debug(String.format("Finish GetNews id: %s", item));
        
        return CompletableFuture.completedFuture(hacker);
    }
}
