package com.example.demo.domain;

/**
 * Created by Jakub krhovják on 1/9/18.
 */
public class SynchronizedClass {

	public static void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static synchronized int staticSynchronizedMethodA() {
		System.out.println("in staticSynchronyzedMethod A");
		sleep();
		return 0;
	}

	public static synchronized int staticSynchronizedMethodB() {
		System.out.println("in staticSynchronyzedMethod B");
		sleep();
		return 0;
	}


	public synchronized int a() {
		System.out.println("in method A");
		sleep();
		return 0;
	}

	public synchronized int b() {
		System.out.println("in method B");
		sleep();
		return 0;
	}

}
