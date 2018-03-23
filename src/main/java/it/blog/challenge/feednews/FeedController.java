package it.blog.challenge.feednews;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import it.blog.challenge.feednews.ingestion.FeedIngestion;

@RestController
public class FeedController {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	FeedIngestion ingestion;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/news")
	public List<Feed> getNewsAll() {

		try {
			/*
			 * Get Feeds
			 */
			log.info("Get News...");
			List<Feed> feed = ingestion.ingestionFeeds();

			/*
			 * Sorting
			 */
			log.info("Sorting News...");
			List<Feed> sortedFeed = feed.stream().sorted().collect(Collectors.toList());

			return sortedFeed;
		} catch (Exception e) {
			log.error("Something goes wrong", e);
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
			log.info("Get News...");
			List<Feed> feed = ingestion.ingestionFeeds();

			/*
			 * Filter and sorted
			 */
			log.info("Sorting News...");
			List<Feed> sortedFeed = feed.stream().filter(o -> o.getSource().equals(source)).sorted()
					.collect(Collectors.toList());

			return sortedFeed;
		} catch (Exception e) {
			log.error("Something goes wrong", e);
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
			log.info("Get News...");
			List<Feed> feed = ingestion.ingestionFeeds();

			/*
			 * Insert into MongoDb
			 */
			log.info("Insert news into MongoDb...");
			mongoTemplate.insert(feed, "feed");

			return new OperationSuccess("Success");
			
		} catch (Exception e) {
			log.error("Something goes wrong", e);
			return new GeneralError(e.getMessage());
		}
	}

	@RequestMapping("/list")
	public List<Feed> list() {

		try {
			/*
			 * Get all data
			 */
			log.info("Get News from Database...");
			List<Feed> feed = mongoTemplate.findAll(Feed.class, "feed");

			/*
			 * Sorted Collection
			 */
			log.info("Sorting News...");
			List<Feed> sortedFeed = feed.stream().sorted().collect(Collectors.toList());

			return sortedFeed;
		} catch (Exception e) {
			log.error("Something goes wrong", e);
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
			log.info(String.format("Get News from Database using the word %s", word));
			
			Query query = new Query();
			query.addCriteria(Criteria.where("title").regex(word));

			List<Feed> feed = mongoTemplate.find(
					query, Feed.class);
			
			/*
			 * Sorted Collection
			 */
			log.info("Sorting News...");
			List<Feed> sortedFeed = feed.stream().sorted().collect(Collectors.toList());

			return sortedFeed;
		} catch (Exception e) {
			log.error("Something goes wrong", e);
			return new ArrayList<Feed>() {
				{
					add(new GeneralError(e.getMessage()));
				}
			};
		}
	}

}
