package com.chronimo.bookmarks;

import java.util.Date;
import java.util.List;

/**
 * A generic bookmark
 * 
 * @author fgutmann
 */
public class Bookmark {
	
	/**
	 * URL of the bookmark
	 */
	private String url;
	/**
	 * date when the bookmark was added
	 */
	private Date addDate;
	/**
	 * title of the bookmark
	 */
	private String title;
	/**
	 * long description of the bookmark
	 */
	private String description;
	/**
	 * tags of the bookmark
	 */
	private List<String> tags;
	
	public Bookmark() {}
	
	public Bookmark(Date addDate, String url, String title, String description, List<String> tags) {
		this.addDate = addDate;
		this.url = url;
		this.title = title;
		this.description = description;
		this.tags = tags;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
