package com.example.demo;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.Callable;

import static com.example.demo.MethodTest.COUNT;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Jakub Krhovják on 5/17/18.
 */
class Adder implements Callable<Long> {



	public long add(long loopSize) {
		long counter = 0;
		for (int i = 0; i < loopSize; i++) {
			for (int j = 0; j < loopSize; j++) {
               ++counter;
			}
		}
		return counter;
	}

	@Override
	public Long call() throws Exception {
		return add(COUNT);
	}
}

@Slf4j

public class MethodTest {

	public static final int COUNT = 1000;

	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();

	@Rule
	public RepeatingRule repeatedly = new RepeatingRule();

	private Adder adder = new Adder();

	@Test
	@Repeating(repetition = 100)
	@Concurrent(count = 8)
	public void simpleAdderTest() {
		assertThat(adder.add(COUNT)).isEqualTo(COUNT * COUNT);
	}


}
