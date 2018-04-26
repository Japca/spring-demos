package com.example.demo.domain;

import java.util.*;

/**
 * Created by Jakub krhovják  on 7/4/17.
 */
public class Item {

	private static int ID = 0;

	private Integer id;

	private String name;

	private Date created;

	private List<Comment> comments = new ArrayList<>();

	public Item() {
		id = ++ID;
		name = "Item" + id;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, id);
		created = calendar.getTime();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		name = name;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void addIComment(Comment comment) {
		comments.add(comment);
	}
}
