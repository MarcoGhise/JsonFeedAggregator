package it.blog.challenge.feednews.ingestion;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import it.blog.challenge.feednews.bean.Feed;
import it.blog.challenge.feednews.bean.bbc.BbcNews;
import it.blog.challenge.feednews.bean.hackernews.HackerList;
import it.blog.challenge.feednews.bean.nytimes.Nytimes;

public interface Ingestion {

	RestTemplate restTemplate(RestTemplateBuilder builder);

	WebClient webClient(RestTemplateBuilder builder);

	/**
	 * 
	 * News From Hacker parallely
	 * 
	 * @return
	 * @throws Exception
	 */
	HackerList getNewsFromHacker() throws InterruptedException, ExecutionException;
	/**
	 *     
	 * News From Ny Times
	 * 
	 * @return
	 */
	Nytimes getNewsFromNyTimes();

	/**
	 * 
	 * News From Bbc
	 * 
	 * @return
	 */
	BbcNews getNewsBbc();

	List<Feed> ingestionFeeds() throws InterruptedException, ExecutionException;

}