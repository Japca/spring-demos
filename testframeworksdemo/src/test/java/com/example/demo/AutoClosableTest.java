package com.example.demo;

import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Jakub Krhovják on 5/21/18.
 */

class Res1 implements AutoCloseable {

	@Override
	public void close() throws Exception {
		System.out.println("close res1");
	}
}


class Res2 implements Closeable {

	@Override
	public void close() throws IOException {
		throw new RuntimeException("Exception res2");
	}
}

public class AutoClosableTest {

	@Test
	public void closableTest() {
		try(Res1 res1 = new Res1(); Res2 res2= new Res2()) {
			System.out.println("In try");
		} catch (Exception e) {
			System.out.println("In Catch " + e.getClass());
		} finally {
			System.out.println("In finally");
		}
	}
}
