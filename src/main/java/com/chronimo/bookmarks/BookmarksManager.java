package com.chronimo.bookmarks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


/**
 * A BookmarksManager is used to manage bookmarks.
 * This includes reading from a file, modifying and writing to a file.
 * 
 * @author fgutmann
 */
public interface BookmarksManager {
	
	/**
	 * Reads the bookmarks from the given input file.
	 */
	public void read(File file) throws FileNotFoundException, IOException;
	
	/**
	 * Writes the changes back to the file system.
	 */
	public void write(File file) throws IOException;
	
	/**
	 * Adds a bookmark to the to the Manager.
	 */
	public void addBookmark(Bookmark bookmark);
	
	/**
	 * Get a list of all bookmarks.
	 */
	public List<Bookmark> getBookmarks();
}
