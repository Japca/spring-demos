package com.example.demo.service;

/**
 * Created by Jakub krhovják on 7/4/17.
 */
public class AtomicIdGenerator {

	private static Long nextId = System.currentTimeMillis();

	public Long nextId() {
		return nextId++;
	}

}
