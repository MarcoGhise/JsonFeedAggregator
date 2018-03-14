package it.blog.challenge.feednews.bean.hackernews;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.blog.challenge.feednews.bean.Feed;


public class Hacker extends Feed
{

	private final static String SOURCE = "Hacker";

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private String time;


    public Hacker()
    {
    	this.setSource(SOURCE); 
    }
    public String getTime ()
    {
        return time;
    }

    /*
     * 1520999430
     */
    public void setTime (String time)
    {
    	Instant i = Instant.ofEpochSecond(Long.valueOf(time));
    	
    	Date date = Date.from(i);
    	  
    	this.setPublishDate(date);
        this.time = time;
    }

}