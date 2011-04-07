package com.chronimo.bookmarks.firefox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.chronimo.bookmarks.Bookmark;
import com.chronimo.bookmarks.BookmarksManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Implementation of BookmarksManager for the firefox browser.
 * 
 * @author fgutmann
 */
public class FirefoxManager implements BookmarksManager {
	
	private Gson gson;
	private Node rootNode;
	
	private int maxId;
	private Node unsortedBookmarksNode;
	private Node tagNode;
	private HashMap<String, Node> tags = new HashMap<String, Node>();
	
	private final String tagNodeRoot = "tagsFolder";
	private final String unsortedBookmarksRoot = "unfiledBookmarksFolder";
	
	/**
	 * Default constructor
	 */
	public FirefoxManager() {
		initializeGson();
	}
	
	/**
	 * Intializes and configures gson
	 */
	protected void initializeGson() {
		GsonBuilder gsonBuilder = new GsonBuilder().disableHtmlEscaping();
		
		// Date type adapters
		gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				return new Date(json.getAsLong() / 1000);
			}
		});
		gsonBuilder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
			public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
				return new JsonPrimitive(src.getTime() * 1000);
			}
		});
		this.gson = gsonBuilder.create();		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void read(File file) throws FileNotFoundException {
		InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
		rootNode = gson.fromJson(reader, Node.class);
		
		rootNode.visit(new NodeVisitor() {
			public void visit(Node node) {
				// find the max id
				if (node.getId() > maxId) {
					maxId = node.getId();
				}
				// find the unsorted bookmarks node
				if (unsortedBookmarksRoot.equals(node.getRoot())) {
					unsortedBookmarksNode = node;
				}
				// find the tag node
				if (tagNodeRoot.equals(node.getRoot())) {
					tagNode = node;
				}
				// build the tags map
				if(tagNode != null && node.getParent() == tagNode.getId()) {
					tags.put(node.getTitle(), node);
				}
			}
		});
	}
	
	/**
	 * Writes the changes back to the file system.
	 */
	public void write(File file) throws IOException {
		String json = gson.toJson(rootNode);
		FileUtils.write(file, json, "UTF-8");
	}
	
	/**
	 * Adds a bookmark to the manager.
	 */
	public void addBookmark(Bookmark bookmark) {
		Node node = new Node();
		// Default properties
		node.setType("text/x-moz-place");
		node.setCharset("UTF-8");
		node.setLastModified(new Date());
		
		// Bookmark properties
		node.setTitle(bookmark.getTitle());
		node.setDateAdded(bookmark.getAddDate());
		node.setUri(bookmark.getUrl());
		
		// Description Annotation
		if(bookmark.getDescription() != null) {
			Annotation annotation = new Annotation();
			annotation.setName("bookmarkProperties/description");
			annotation.setFlags(0);
			annotation.setExpires(4);
			annotation.setMimeType(null);
			annotation.setType(3);
			annotation.setValue(bookmark.getDescription());
		}
		
		insertAsBookmark(node);
		for(String tag : bookmark.getTags()) {
			insertAsTag(tag, node);
		}
	}
	
	/**
	 * Currently not implemented
	 */
	public List<Bookmark> getBookmarks() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Inserts a node as bookmark
	 * @param node The node to insert
	 */
	private void insertAsBookmark(Node node) {
		Node copy = new Node(node);
		insertNode(copy, unsortedBookmarksNode);
	}
	
	/**
	 * Inserts a node as a tag
	 * @param tagString The tag in form of a string
	 * @param node The node to insert 
	 */
	private void insertAsTag(String tagString, Node node) {
		Node copy = new Node(node);
		copy.setAnnos(null);
		
		Node tagNode = this.tags.get(tagString);
		if (tagNode == null) {
			tagNode = createTagNode(tagString);
		}
		insertNode(copy, tagNode);
	}
	
	/**
	 * Creates a new tag node
	 */
	private Node createTagNode(String tagString) {
		Date now = new Date();
		Node node = new Node();
		node.setTitle(tagString);
		node.setDateAdded(now);
		node.setLastModified(now);
		node.setType("text/x-moz-place-container");
		
		insertNode(node, this.tagNode);
		this.tags.put(tagString, node);
		return node;
	}
	
	/**
	 * Inserts a node as child of another node
	 * @param node Node to insert
	 * @param parent The parent node
	 */
	private void insertNode(Node node, Node parent) {
		node.setId(++ maxId);
		node.setParent(parent.getId());
		
		parent.insert(node);
	}
}
