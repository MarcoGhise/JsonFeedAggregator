package it.blog.challenge.feednews;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.blog.challenge.feednews.bean.Feed;
import it.blog.challenge.feednews.bean.GeneralError;
import it.blog.challenge.feednews.bean.OperationSuccess;
import it.blog.challenge.feednews.bean.bbc.BbcNews;
import it.blog.challenge.feednews.bean.hackernews.HackerList;
import it.blog.challenge.feednews.bean.nytimes.Nytimes;
import it.blog.challenge.feednews.ingestion.FeedIngestion;

@RestController
public class FeedController {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	FeedIngestion ingestion;

	@RequestMapping("/news")
	public List<Feed> getNewsAll() {

		try {
			/*
			 * Get Feeds
			 */
			List<Feed> feed = ingestion.ingestionFeeds();

			/*
			 * Sorting
			 */
			List<Feed> sortedFeed = feed.stream().sorted().collect(Collectors.toList());

			return sortedFeed;
		} catch (Exception e) {
			return new ArrayList<Feed>() {
				{
					add(new GeneralError(e.getMessage()));
				}
			};
		}

	}

	@RequestMapping("/news/{source}")
	public List<Feed> getNewsSource(@PathVariable("source") String source) {

		try {
			/*
			 * Get Feeds
			 */
			List<Feed> feed = ingestion.ingestionFeeds();

			/*
			 * Filter and sorted
			 */
			List<Feed> sortedFeed = feed.stream().filter(o -> o.getSource().equals(source)).sorted()
					.collect(Collectors.toList());

			return sortedFeed;
		} catch (Exception e) {
			return new ArrayList<Feed>() {
				{
					add(new GeneralError(e.getMessage()));
				}
			};
		}
	}

	@RequestMapping("/save")
	public Feed save() throws ParseException {

		try {
			/*
			 * Get Feeds
			 */
			List<Feed> feed = ingestion.ingestionFeeds();

			/*
			 * Insert into MongoDb
			 */
			mongoTemplate.insert(feed, "feed");

			return new OperationSuccess("Success");
			
		} catch (Exception e) {
			
			return new GeneralError(e.getMessage());
		}
	}

	@RequestMapping("/list")
	public List<Feed> list() {

		try {
			/*
			 * Get all data
			 */
			List<Feed> feed = mongoTemplate.findAll(Feed.class, "feed");

			/*
			 * Sorted Collection
			 */
			List<Feed> sortedFeed = feed.stream().sorted().collect(Collectors.toList());

			return sortedFeed;
		} catch (Exception e) {
			return new ArrayList<Feed>() {
				{
					add(new GeneralError(e.getMessage()));
				}
			};
		}
	}
	
	@RequestMapping("/list/{word}")
	public List<Feed> listByWord(@PathVariable String word) {

		try {
			 
			/*
			 * Get data filtered
			 */			
			Query query = new Query();
			query.addCriteria(Criteria.where("title").regex(word));

			List<Feed> feed = mongoTemplate.find(
					query, Feed.class);
			
			/*
			 * Sorted Collection
			 */
			List<Feed> sortedFeed = feed.stream().sorted().collect(Collectors.toList());

			return sortedFeed;
		} catch (Exception e) {
			return new ArrayList<Feed>() {
				{
					add(new GeneralError(e.getMessage()));
				}
			};
		}
	}

}
