package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jakub Krhovják on 5/3/18.
 */

@Slf4j
public class PhaserTest {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Phaser phaser = new Phaser();
		log.info("Main Parties {}" , phaser.getRegisteredParties());
		log.info("Main unarived {}" , phaser.getUnarrivedParties());
		log.info("Phase: {} ", phaser.getPhase());

		Task task1 = new Task("thread-1", phaser);
		Task task2 = new Task("thread-2", phaser);
		Task task3 = new Task("thread-3", phaser);

		log.info("Main Parties {}" , phaser.getRegisteredParties());
		log.info("Main unarived {}" , phaser.getUnarrivedParties());
		log.info("Phase: {} ", phaser.getPhase());
		executorService.execute(task1);
		executorService.execute(task2);
		TimeUnit.SECONDS.sleep(5);
		executorService.execute(task3);
//		log.info("Phase: {} ", phaser.getPhase());
//		TimeUnit.SECONDS.sleep(2);
//		log.info("Main Parties {}" , phaser.getRegisteredParties());
//		log.info("Main unarived {}" , phaser.getUnarrivedParties());
//		phaser.arriveAndDeregister();
//		log.info("Phase: {} ", phaser.getPhase());
//		log.info("End fmain thread");


	}


}

@Slf4j
class Task implements Runnable {
	private String threadName;
	private Phaser phaser;

	int i = 0;
	Task(String threadName, Phaser phaser) {
		Thread.currentThread().setName(threadName);
		this.threadName = threadName;
		this.phaser = phaser;
		phaser.register();
	}

	@Override
	public void run() {
		log.info("-------------------------------------------------------------------");
		log.info("in run");
		log.info("Parties {}" , phaser.getRegisteredParties());
		log.info("unarived {}" , phaser.getUnarrivedParties());
		log.info("Phase: {} ", phaser.getPhase());
		phaser.arriveAndAwaitAdvance();
		log.info("after wait Phase: {} ", phaser.getPhase());
			try {

			TimeUnit.SECONDS.sleep(3 + i);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		log.info("-------------------------------------------------------------------");
		log.info("after wait 2 Phase: {} ", phaser.getPhase());
		log.info("Parties {}" , phaser.getRegisteredParties());
		log.info("unarived {}" , phaser.getUnarrivedParties());


		phaser.arriveAndAwaitAdvance();

		log.info("End thread: {} ", Thread.currentThread().getName());
	}
}