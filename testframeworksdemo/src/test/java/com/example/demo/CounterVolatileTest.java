package com.example.demo;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by Jakub Krhovják on 4/17/18.
 */
public class CounterVolatileTest {

	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();

	@Rule
	public RepeatingRule repeatedly = new RepeatingRule();


	//	@Concurrent(count = 8)
	@Repeating(repetition = 10000)
	@Test
	public void synchronizedWorkingTest() {

		Counter counter = new Counter();

		CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> IntStream.range(0, 100).forEach(i -> counter.increment()));
		CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.runAsync(() -> IntStream.range(0, 100).forEach(i -> counter.increment()));
		CompletableFuture.allOf(voidCompletableFuture, voidCompletableFuture1).join();
		assertThat(counter.count).isEqualTo(200);
	}

	@Repeating(repetition = 1000)
	@Test
	public void volatileIsWorkingTest() {
		Counter counter = new Counter();

		CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> IntStream.range(0, 100).forEach(i -> counter.add()));
		CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.runAsync(() -> IntStream.range(0, 100).forEach(i -> counter.add()));
		CompletableFuture.allOf(voidCompletableFuture, voidCompletableFuture1).join();
		assertThat(counter.count).isEqualTo(200);
	}
}
