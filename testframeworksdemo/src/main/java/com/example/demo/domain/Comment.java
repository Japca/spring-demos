package com.example.demo.domain;

/**
 * Created by Jakub krhovják on 7/4/17.
 */
public class Comment {

	private static int ID = 0;

	private Integer id;

	private String name;

	private String content;

	public Comment() {
		id = ++ID;
		name = "Comment_" + id;
		content = name + "content";
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
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
