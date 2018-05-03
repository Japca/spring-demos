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
		Phaser phaser = new Phaser(1);
		log.info("Main Parties {}" , phaser.getRegisteredParties());
		log.info("Main unarived {}" , phaser.getUnarrivedParties());
		log.info("Phase: {} ", phaser.getPhase());

		executorService.execute(new Task("thread-1", phaser));
		executorService.execute(new Task("thread-2", phaser));
		executorService.execute(new Task("thread-3", phaser));
		log.info("Phase: {} ", phaser.getPhase());
		TimeUnit.SECONDS.sleep(2);
		log.info("Main Parties {}" , phaser.getRegisteredParties());
		log.info("Main unarived {}" , phaser.getUnarrivedParties());
		phaser.arriveAndDeregister();
		log.info("Phase: {} ", phaser.getPhase());
		log.info("End fmain thread");


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
		log.info("in run");
		phaser.arriveAndAwaitAdvance();
		log.info("after wait");
		try {

			TimeUnit.SECONDS.sleep(3 + i);
			synchronized (this) {
				if (i <= 0) {
					log.info("Parties {}" , phaser.getRegisteredParties());
					log.info("unarived {}" , phaser.getUnarrivedParties());
					phaser.register();
					log.info("Parties {}" , phaser.getRegisteredParties());
					log.info("unarived {}" , phaser.getUnarrivedParties());
					++i;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		log.info("Parties {}" , phaser.getRegisteredParties());
		log.info("unarived {}" , phaser.getUnarrivedParties());
		if(i >= 1) {
			phaser.arriveAndDeregister();
			log.info("Parties {}" , phaser.getRegisteredParties());
			log.info("unarived {}" , phaser.getUnarrivedParties());
		}
		phaser.arriveAndAwaitAdvance();
		log.info("Phase: {} ", phaser.getPhase());
		log.info("Parties {}" , phaser.getRegisteredParties());
		log.info("unarived {}" , phaser.getUnarrivedParties());
		log.info("End thread: {} ", Thread.currentThread().getName());
	}
}