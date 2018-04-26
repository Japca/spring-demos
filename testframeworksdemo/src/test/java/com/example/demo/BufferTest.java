package com.example.demo;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Jakub krhovják on 11/11/17.
 */


public class BufferTest {


	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();

	@Rule
	public RepeatingRule repeatedly = new RepeatingRule();

	ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

	ScheduledExecutorService scheduledExecutorService2 = Executors.newScheduledThreadPool(1);

	int added;

	//	private List<Integer> numbers = new CopyOnWriteArrayList<>();
//	private List<Integer> readNumbers = new ArrayList<>();
	private int number = 0;

	@Test
	@Repeating(repetition = 100)
	public void bufferTest() throws Exception {
		ConcurrentLinkedQueue<Integer> numbers = new ConcurrentLinkedQueue<>();

		//	List<Integer> numbers = Collections.synchronizedList(new ArrayList<>());
		List<Integer> readNumbers = new ArrayList<>();
		ScheduledFuture scheduledFuture =
				scheduledExecutorService.scheduleWithFixedDelay(() -> {
					IntStream.range(0, 10).forEach(i -> {
//					synchronized (this) {
						numbers.add(++number);

						++added;
//					}
					});

				}, 0L, 50, TimeUnit.MILLISECONDS);

		ScheduledFuture scheduledFutureRead =
				scheduledExecutorService2.scheduleWithFixedDelay(() -> {
//					synchronized (this) {
					//readNumbers.addAll(numbers);
					//numbers.clear();
					numbers.forEach(number -> readNumbers.add(numbers.poll()));
					//	readNumbers.addAll(numbers);
					//	numbers.clear();
//					}
				}, 0L, 1, TimeUnit.MILLISECONDS);

		Thread.sleep(400);
		scheduledFuture.cancel(false);
		//scheduledFutureRead.cancel(false);
		Thread.sleep(200);
		//System.out.println(readNumbers);
		assertThat(readNumbers.size()).isEqualTo(added);
		added = 0;
		scheduledFuture.cancel(false);
		Thread.sleep(100);


		System.out.println(readNumbers.size());

		System.out.println("END!");
	}

	ArrayList<Integer> syncData = new ArrayList<>();
	ArrayList<Integer> resultSyncData = new ArrayList<>();
	ConcurrentLinkedQueue<Integer> queueData = new ConcurrentLinkedQueue<>();
	ConcurrentLinkedQueue<Integer> resultQueueData = new ConcurrentLinkedQueue<>();

	@Test
	public void race() throws Exception {
		boolean end = false;

		int recordsToAdd = 1000000;
		int timeOut = 500;

//		IntStream.range(0, 10).forEach(i -> {
//			syncData.add(i);
//			queueData.add(i);
//		});

		scheduledExecutorService.scheduleAtFixedRate(() -> {
			IntStream.range(0, recordsToAdd).forEach(i -> {
				synchronized (this) {
					syncData.add(i);
				}
			});

		}, 0L, timeOut, TimeUnit.MILLISECONDS);

		scheduledExecutorService.scheduleAtFixedRate(() -> {
			IntStream.range(0, recordsToAdd).forEach(i -> {
				queueData.add(i);
			});

		}, 0L, timeOut, TimeUnit.MILLISECONDS);

		new Thread(() -> {
			while (end == false) {
				synchronized (this) {
					resultSyncData.addAll(syncData);
					syncData.clear();
				}
			}

		}).start();

		new Thread(() -> {
			while (end == false) {
				queueData.forEach(i -> resultQueueData.add(queueData.poll()));
			}

		}).start();

		Thread.sleep(1000);
		//end = true;
		//	Thread.sleep(100);
		System.out.println("SyncData: " + resultSyncData.size());
		System.out.println("QueueData: " + resultQueueData.size());
	}


}


