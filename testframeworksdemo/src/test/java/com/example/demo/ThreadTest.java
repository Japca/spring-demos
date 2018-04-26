package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Jakub Krhovják on 4/26/18.
 */
@Slf4j
public class ThreadTest {

	private Object lock  = new Object();

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
}
