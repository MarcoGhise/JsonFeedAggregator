package it.blog.challenge.feednews.ingestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import it.blog.challenge.feednews.bean.Feed;
import it.blog.challenge.feednews.bean.bbc.BbcNews;
import it.blog.challenge.feednews.bean.hackernews.Hacker;
import it.blog.challenge.feednews.bean.hackernews.HackerList;
import it.blog.challenge.feednews.bean.nytimes.Nytimes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Profile("Reactive")
public class FeedIngestionReactive implements Ingestion {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${feed.nytimes.url}")
	String nyTimesUrl;

	@Value("${feed.hacker.list.url}")
	String hackerListUrl;

	@Value("${feed.hacker.detail.url}")
	String hackerDetailUrl;

	@Value("${feed.bbc.url}")
	String bbcUrl;

	@Value("${hacker.article.limit}")
	int hackerArticleLimit;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	AsyncNews asyncNews;
	
	@Autowired
	WebClient client;

	/* (non-Javadoc)
	 * @see it.blog.challenge.feednews.ingestion.Ingestion#restTemplate(org.springframework.boot.web.client.RestTemplateBuilder)
	 */
	@Override
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	/* (non-Javadoc)
	 * @see it.blog.challenge.feednews.ingestion.Ingestion#webClient(org.springframework.boot.web.client.RestTemplateBuilder)
	 */
	@Override
	@Bean
	public WebClient webClient(RestTemplateBuilder builder) {
		return WebClient.create();
	}

	
	/* (non-Javadoc)
	 * @see it.blog.challenge.feednews.ingestion.Ingestion#getNewsFromHackerReactive()
	 */
	@Override
	public HackerList getNewsFromHacker() throws InterruptedException, ExecutionException {

		log.debug("Start getting news from NewsHacker...");
		
		String[] articleId = restTemplate.getForObject(hackerListUrl, String[].class);

		articleId = Arrays.copyOf(articleId, hackerArticleLimit);
		
		HackerList hackerList = new HackerList();

		log.debug(String.format("Number of articles: %s", articleId.length));
		
		List<Hacker> hackers = Flux.fromArray(articleId)
				.log() //
				.flatMap(this::fetch) // <2>
				.collectList().block();
		
		hackerList.setHacker(hackers);

		log.debug("Finish getting news from NewsHacker");
		
		return hackerList;
	}

	private Mono<Hacker> fetch(String item) {
		
		String url = String.format(hackerDetailUrl, item);
		
		return this.client.get().uri(url).retrieve().bodyToMono(Hacker.class);
				
	}
	
	/* (non-Javadoc)
	 * @see it.blog.challenge.feednews.ingestion.Ingestion#getNewsFromNyTimes()
	 */
	@Override
	public Nytimes getNewsFromNyTimes() {

		log.debug("Start getting news from Nyt...");
		
		Nytimes nytimes = restTemplate.getForObject(nyTimesUrl, Nytimes.class);
		
		log.debug("Finish getting news from Nyt...");

		return nytimes;

	}

	/* (non-Javadoc)
	 * @see it.blog.challenge.feednews.ingestion.Ingestion#getNewsBbc()
	 */
	@Override
	public BbcNews getNewsBbc() {

		log.debug("Start getting news from Bbc...");
		
		BbcNews bbc = restTemplate.getForObject(bbcUrl, BbcNews.class);

		log.debug("Finish getting news from Nyt...");
		
		return bbc;

	}

	/* (non-Javadoc)
	 * @see it.blog.challenge.feednews.ingestion.Ingestion#ingestionFeeds()
	 */
	@Override
	public List<Feed> ingestionFeeds() throws InterruptedException, ExecutionException {
		     
		log.debug("Start Feed ingestion...");
		/*
		 * Get news 
		 */
		HackerList hackerList = this.getNewsFromHacker();
		log.debug("Got Hackernews...");
	
		Nytimes nytimes = this.getNewsFromNyTimes();
		log.debug("Got Nyt...");
		
		BbcNews bbc = this.getNewsBbc();
		log.debug("Got Bbc...");

		/*
		 * Aggregate
		 */
		log.debug("Start Feed aggregation...");
		List<Feed> feed = new ArrayList<Feed>();
		feed.addAll(hackerList.getHacker());
		feed.addAll(Arrays.asList(nytimes.getResults()));
		feed.addAll(Arrays.asList(bbc.getArticles()));

		log.debug("Aggregation completed!");
		
		return feed;
	}

}
