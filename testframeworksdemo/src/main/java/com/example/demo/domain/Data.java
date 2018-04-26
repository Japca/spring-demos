package com.example.demo.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jakub krhovják on 7/3/17.
 */
public class Data {

	private static int ID = 0;

	private Integer id;

	private String name;

	private Map<String, Integer> custom = new HashMap<>();

	public Data(String name) {
		this.id = ++ID;
		this.name = name;
	}

	public void put(String key, Integer value) {
		custom.put(key, value);
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Map<String, Integer> getCustom() {
		return custom;
	}
}

