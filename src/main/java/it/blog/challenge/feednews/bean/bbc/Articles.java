package it.blog.challenge.feednews.bean.bbc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.blog.challenge.feednews.bean.Feed;
import it.blog.challenge.feednews.bean.nytimes.Source;

public class Articles extends Feed
{
    private String publishedAt;
    
    @JsonProperty(value="source", access = JsonProperty.Access.WRITE_ONLY)
    private Source articleSource;

	private final static String SOURCE = "Bbc";
	private final static String TYPE = "News";
	
    public Articles()
    {
    	this.setSource(SOURCE);
    	this.setType(TYPE);
    }
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    public String getPublishedAt ()
    {
        return publishedAt;
    }

    /*
     * 2018-03-14T07:54:22Z
     */
    public void setPublishedAt (String publishedAt) throws ParseException
    {    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		Date d = sdf.parse(publishedAt);

     	this.setPublishDate(d);
        this.publishedAt = publishedAt;
    }
   
    public Source getArticleSource ()
    {
        return articleSource;
    }

    public void setSource (Source source)
    {
        this.articleSource = source;
    }

}