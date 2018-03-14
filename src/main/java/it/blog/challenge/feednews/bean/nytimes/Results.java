package it.blog.challenge.feednews.bean.nytimes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.blog.challenge.feednews.bean.Feed;


public class Results extends Feed
{

	private final static String SOURCE = "NyTimes";
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Transient
    private String published_date;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private String item_type;

    public Results()
    {
    	this.setSource(SOURCE);
    }
    
    public String getPublished_date ()
    {
        return published_date;
    }

    /*
     * 2018-03-14T02:40:06-04:00
     */
    public void setPublished_date (String published_date) throws ParseException
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		
		Date d = sdf.parse(published_date);
		
    	this.setPublishDate(d);
        this.published_date = published_date;
    }

    public String getItem_type ()
    {
        return item_type;
    }

    public void setItem_type (String item_type)
    {
    	this.setType(item_type);
        this.item_type = item_type;
    }
    
}