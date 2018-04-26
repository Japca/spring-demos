package com.example.demo;

/**
 * Created by Jakub Krhovják on 4/17/18.
 */
public class Counter {

	public volatile int count = 0;

	public synchronized int increment() {
		return ++count;
	}

	public int add() {
		return count += 1;
	}
}
