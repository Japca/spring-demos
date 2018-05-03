package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jakub Krhovják on 4/26/18.
 */
@Slf4j
public class ThreadTest {

	private Object lock = new Object();

	@Test
	public void name() {

		log.info("start");

		CompletableFuture.runAsync(() -> {
			log.info("Runnable1");
			synchronized (this) {
				try {
					log.info("Runnable1 Sleeping");
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					log.info("Runnable1in catch runnable");
				}
				log.info("Runnable1 notify ");
				notifyAll();
			}
			log.info("Runnable1 end");
		});
		CompletableFuture.runAsync(() -> {
			log.info("Runnable2");
			synchronized (this) {
				try {
					log.info("Runnable2 waiting ...");
					wait();
					log.info("Runnable2 end wait");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			log.info("Runnable2 end");
		});


		synchronized (this) {
			try {
				Thread.sleep(1000);
				log.info("MAIN waiting...");
				wait();
				log.info("MAIN end wait");
			} catch (InterruptedException e) {
				log.info("MAIN in catch");
			}
		}

		log.info("MAIN end thread");
	}

	@Test
	public void isAlive() throws InterruptedException {

		Thread thread = new Thread(() -> {
			try {
				Thread.sleep(2000);
				synchronized (this) {
					notify();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		log.info("is alive {}", thread.isAlive());
		thread.start();


		synchronized (this) {
			log.info("is alive {}", thread.isAlive());
			wait();
			log.info("is alive {}", thread.isAlive());


		}
	}

	@Test
	public void atomicBooleanTest() {
		AtomicBoolean atomicBoolean = new AtomicBoolean(false);
		atomicBoolean.compareAndSet(false, true);
		log.info("is {}", atomicBoolean.get());

		AtomicInteger atomicInteger = new AtomicInteger(2);
		atomicInteger.compareAndSet(1, 3);
		log.info("is {}", atomicInteger.get());

		log.info("is {}", atomicInteger.addAndGet(2));
		log.info("is {}", atomicInteger.getAndAdd(2));
		log.info("is {}", atomicInteger.get());


	}


	@Test
	public void exceptionCatch() {

		try {
			throw new RuntimeException("RuntimeException");
		} catch (Exception e) {
			log.info("exception caught!");
		}

//		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
//			log.info("Exception caught in Global handler.threadName:{}, ExceptionName:{}", t.getName(), e.getMessage() );
//		});

		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				log.info("Exception caught in Global handler.threadName:{}, ExceptionName:{}", t.getName(), e.getMessage() );
			}

			@Override
			public String toString() {
				return "in to sring adfsdfsdd";
			}
		});
		runThread();

	}

	private void runThread() {

		new Thread(() -> {
			throw new RuntimeException("RuntimeException");
		}).start();
//		try {
//			CompletableFuture.runAsync(() -> {
//				throw new RuntimeException("RuntimeException");
//			});
//		} catch (Exception e) {
//			log.info("exception caught in thread!");
//
//		}
	}
}
