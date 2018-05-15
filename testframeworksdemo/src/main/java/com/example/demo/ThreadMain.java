package com.example.demo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Jakub Krhovják on 4/26/18.
 */

@Slf4j
class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		log.error("Exception:", e);
	}
}
class CustomThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		thread.setUncaughtExceptionHandler(new CustomExceptionHandler());
		return thread;
	}
}


@Slf4j
public class ThreadMain {

	public static void main(String[] args) throws InterruptedException {

		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			log.info("Exception caught in Global handler.threadName:{}, ExceptionName:{}", t.getName(), e.getMessage() );
		});


		new Thread(() -> {
			throw new RuntimeException("RuntimeException");
		}).start();

		Thread t2 = new Thread(() -> {
			throw new RuntimeException("RuntimeException");
		});
		t2.setUncaughtExceptionHandler((t, e) -> {
			log.info("Exception caught in Local handler.threadName:{}, ExceptionName:{}", t.getName(), e.getMessage() );
		});
		t2.start();


		final ThreadFactory threadFactory = new ThreadFactoryBuilder()
				.setNameFormat("GUAVA thread")
				.setUncaughtExceptionHandler((t, e) -> {
					log.info("Exception caught in GUAVA handler.threadName:{}, ExceptionName:{}", t.getName(), e.getMessage() );
				})
				.build();


		ExecutorService service = Executors.newCachedThreadPool(threadFactory);
		service.execute(() -> {
			throw new RuntimeException("RuntimeException");
		});

		ExecutorService service2 = Executors.newCachedThreadPool(new CustomThreadFactory());
		service2.execute(() -> {
			throw new RuntimeException("RuntimeException2");
		});

		service.shutdown();
		service2.shutdown();

//			CompletableFuture.runAsync(() -> {
//				throw new RuntimeException("RuntimeException");
//			});
//

	}
}
