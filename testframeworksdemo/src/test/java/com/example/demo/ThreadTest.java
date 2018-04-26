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
			log.info("in runnable");
			synchronized (this) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					log.info("in catch runnable");
				}
				log.info("notify");
				notify();
			}
			log.info("end runnable");
		});

		synchronized (this) {
			try {
				log.info("waiting...");
				wait();
				log.info("end wait");
			} catch (InterruptedException e) {
				log.info("in catch");
			}
		}

		log.info("end thread");
	}
}
