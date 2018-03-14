package it.blog.challenge.feednews.bean;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class Feed implements Comparable<Feed> {

	private String title;
	private String url;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date publishDate;
	private String type;
	private String source;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public int compareTo(Feed o) {
		return o.getPublishDate().compareTo(getPublishDate());
	}

}
