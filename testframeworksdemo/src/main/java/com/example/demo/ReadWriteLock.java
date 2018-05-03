package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Jakub Krhovják on 5/2/18.
 */


public class ReadWriteLock {

	private static int value = 0;



	public static void main(String[] args) {
		final Handler writer1 = new Handler();
		Handler reader1 = new Handler();
		Handler reader2 = new Handler();
		List<Thread> writerThreads = new ArrayList<>();

		for(int i = 0; i < 10; i++) {
			int finalI = i;
			Thread thread = new Thread(() -> {
				writer1.write(finalI);
			});
			writerThreads.add(thread);


			thread.start();
		}

		for(int i = 0; i < 10; i++) {
			int finalI = i;
			Thread thread = new Thread(() -> {
				reader1.read();
			});
			thread.setName("reader1");
			thread.start();
		}

		for(int i = 0; i < 10; i++) {
			int finalI = i;
			Thread thread = new Thread(() -> {
				reader2.read();
			});
			thread.setName("reader2");
			thread.start();
		}

		writerThreads.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

	}




}

@Slf4j
class Handler {

	private java.util.concurrent.locks.ReadWriteLock lock = new ReentrantReadWriteLock();

	private int value = 0;

	public void write(int value) {
		lock.writeLock().lock();
		log.info("Writing value: {}", value);
		this.value = value;
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Value written: {}", value);
		lock.writeLock().unlock();
	}

	public void read() {
		lock.readLock().lock();
		log.info("reading value value:");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Value is: {}", value);
		lock.readLock().unlock();
	}

}



