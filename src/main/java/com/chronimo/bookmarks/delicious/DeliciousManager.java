package com.chronimo.bookmarks.delicious;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.chronimo.bookmarks.Bookmark;
import com.chronimo.bookmarks.BookmarksManager;

/**
 * Implementation of a BookmarksParser for delicious htm bookmark file.
 * 
 * @author fgutmann
 */
public class DeliciousManager implements BookmarksManager {
	
	private ArrayList<Bookmark> bookmarks;
	
	/**
	 * {@inheritDoc}
	 */
	public void read(File file) throws IOException {
		List<String> lines = FileUtils.readLines(file, "UTF-8");
		bookmarks = new ArrayList<Bookmark>(lines.size());
		
		Pattern bookmarkPattern = Pattern.compile(".*?<DT><A HREF=\"([^\"]*)\" ADD_DATE=\"([^\"]*)\".*? TAGS=\"([^\"]*)\">([^<]*)</A>");
		Pattern descriptionPattern = Pattern.compile("<DD>(.*)");
		
		for (String line : lines) {
			Matcher matcher = bookmarkPattern.matcher(line);
			if (matcher.matches()) {
				Bookmark bookmark = new Bookmark();
				
				bookmark.setUrl(matcher.group(1));
				bookmark.setAddDate(new Date(Long.parseLong(matcher.group(2))*1000));
				bookmark.setTags(Arrays.asList(matcher.group(3).split(",")));
				bookmark.setTitle(matcher.group(4));
				bookmarks.add(bookmark);
			} else {
				matcher = descriptionPattern.matcher(line);
				if (matcher.matches()) {
					bookmarks.get(bookmarks.size()-1).setDescription(matcher.group(1));
				}
			}
		}
	}

	/**
	 * Not implemented currently
	 */
	public void write(File file) throws IOException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ArrayList<Bookmark> getBookmarks() {
		return bookmarks;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addBookmark(Bookmark bookmark) {
		this.bookmarks.add(bookmark);
	}
}
