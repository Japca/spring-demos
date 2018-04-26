package com.example.demo;

import com.example.demo.domain.SynchronizedClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jakub krhovják on 1/9/18.
 */
public class SynchronizedTest {

	private long start;
	SynchronizedClass firstInstance = new SynchronizedClass();
	SynchronizedClass secondInstance = new SynchronizedClass();

	@Before
	public void setUp() throws Exception {
		start = System.nanoTime();
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Tooks: " + (System.nanoTime() - start) / 1000_000);
	}

	@Test
	public void test1() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> complete1 = CompletableFuture.supplyAsync(() -> firstInstance.a());
		CompletableFuture<Integer> complete2 = CompletableFuture.supplyAsync(() -> secondInstance.a());
		complete1.get();
		complete2.get();
	}

	@Test
	public void test2() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> complete1 = CompletableFuture.supplyAsync(() -> firstInstance.a());
		CompletableFuture<Integer> complete2 = CompletableFuture.supplyAsync(() -> firstInstance.a());
		complete1.get();
		complete2.get();

	}


	@Test
	public void test3() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> complete1 = CompletableFuture.supplyAsync(() -> firstInstance.a());
		CompletableFuture<Integer> complete2 = CompletableFuture.supplyAsync(() -> firstInstance.b());
		complete1.get();
		complete2.get();
	}

	@Test
	public void test4() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> complete1 = CompletableFuture.supplyAsync(SynchronizedClass::staticSynchronizedMethodA);
		CompletableFuture<Integer> complete2 = CompletableFuture.supplyAsync(SynchronizedClass::staticSynchronizedMethodB);
		complete1.get();
		complete2.get();
	}

	@Test
	public void test5() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> complete1 = CompletableFuture.supplyAsync(SynchronizedClass::staticSynchronizedMethodA);
		CompletableFuture<Integer> complete2 = CompletableFuture.supplyAsync(SynchronizedClass::staticSynchronizedMethodA);
		complete1.get();
		complete2.get();
	}

	@Test
	public void test6() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> complete1 = CompletableFuture.supplyAsync(SynchronizedClass::staticSynchronizedMethodA);
		CompletableFuture<Integer> complete2 = CompletableFuture.supplyAsync(SynchronizedClass::staticSynchronizedMethodA);
		complete1.get();
		complete2.get();
	}

	@Test
	public void test7() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> complete1 = CompletableFuture.supplyAsync(SynchronizedClass::staticSynchronizedMethodA);
		CompletableFuture<Integer> complete2 = CompletableFuture.supplyAsync(() -> firstInstance.b());
		CompletableFuture<Integer> complete3 = CompletableFuture.supplyAsync(() -> secondInstance.b());
		complete1.get();
		complete2.get();
		complete3.get();
	}


}
