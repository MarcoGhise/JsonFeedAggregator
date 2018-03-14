package it.blog.challenge.feednews.ingestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import it.blog.challenge.feednews.bean.Feed;
import it.blog.challenge.feednews.bean.bbc.BbcNews;
import it.blog.challenge.feednews.bean.hackernews.Hacker;
import it.blog.challenge.feednews.bean.hackernews.HackerList;
import it.blog.challenge.feednews.bean.nytimes.Nytimes;

@Component
public class FeedIngestion {

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

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	/**
	 * 
	 * News From Hacker
	 * 
	 * @return
	 * @throws Exception 
	 */
	public HackerList getNewsFromHacker(){
		
		String[] articleId = restTemplate.getForObject(hackerListUrl, String[].class);

		Hacker hacker = null;
		HackerList hackerList = new HackerList();

		List<Hacker> hackerItem = new ArrayList<Hacker>();

		int countOfArticle = 0;

		for (String item : articleId) {
			
			hacker = restTemplate.getForObject(String.format(hackerDetailUrl, item), Hacker.class);

			hackerItem.add(hacker);

			if (countOfArticle > hackerArticleLimit)
				break;

			countOfArticle++;
		}

		hackerList.setHacker(hackerItem);

		return hackerList;
	}

	/**
	 * 
	 * News From Ny Times
	 * 
	 * @return
	 */
	public Nytimes getNewsFromNyTimes() {

		Nytimes nytimes = restTemplate.getForObject(nyTimesUrl, Nytimes.class);

		return nytimes;

	}

	/**
	 * 
	 * News From Bbc
	 * 
	 * @return
	 */
	public BbcNews getNewsBbc() {

		BbcNews bbc = restTemplate.getForObject(bbcUrl, BbcNews.class);

		return bbc;

	}
	
	public List<Feed> ingestionFeeds()
	{
		/*
		 * Get news
		 */		
		HackerList hackerList = this.getNewsFromHacker();
		Nytimes nytimes = this.getNewsFromNyTimes();
		BbcNews bbc = this.getNewsBbc();

		/*
		 * Aggregate
		 */
		List<Feed> feed = new ArrayList<Feed>();
		feed.addAll(hackerList.getHacker());
		feed.addAll(Arrays.asList(nytimes.getResults()));
		feed.addAll(Arrays.asList(bbc.getArticles()));
		
		return feed;
	}

}
