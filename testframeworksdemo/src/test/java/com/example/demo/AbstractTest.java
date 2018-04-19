package com.example.demo;

import org.junit.After;
import org.junit.Before;

/**
 * Created by Jakub krhovják on 11/17/17.
 */
public abstract class AbstractTest {

	private long start;

	@Before
	public void setUp() throws Exception {
		start = System.nanoTime();
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("END test in " +  (System.nanoTime() - start) / 1_000_000);
	}
}
