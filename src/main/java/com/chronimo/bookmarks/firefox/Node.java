package com.chronimo.bookmarks.firefox;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Node {
	
	private String title;
	private int id;
	private Integer parent;
	private Date dateAdded;
	private Date lastModified;
	private String type;
	private String root;
	private String uri;
	private String charset;
	private List<Annotation> annos;
	private List<Node> children;
	
	/**
	 * Default constructor
	 */
	public Node() {}
	
	/**
	 * Copy constructor
	 * @param node
	 */
	public Node(Node node) {
		this.title = node.title;
		this.id = node.id;
		this.parent = node.parent;
		this.dateAdded = node.dateAdded;
		this.lastModified = node.lastModified;
		this.type = node.type;
		this.root = node.root;
		this.children = node.children;
		this.uri = node.uri;
		this.annos = node.annos;
		this.charset = node.charset;
	}
	
	/**
	 * Visit all nodes recursive from top to bottom
	 */
	public void visit(NodeVisitor visitor) {
		visitor.visit(this);
		if (children != null) {
			for (Node child : children) {
				child.visit(visitor);
			}
		}
	}
	
	/**
	 * Inserts a node as child
	 */
	public void insert(Node node) {
		if (children == null) {
			children = new LinkedList<Node>();
		}
		children.add(node);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public List<Node> getChildren() {
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public List<Annotation> getAnnos() {
		return annos;
	}
	public void setAnnos(List<Annotation> annos) {
		this.annos = annos;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
}
