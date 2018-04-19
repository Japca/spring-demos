package com.example.demo;

import com.example.demo.service.CsvReader;
import io.vavr.control.Try;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jakub krhovják on 11/18/17.
 */
public class VavrTest extends  AbstractTest {

	private Logger log = LoggerFactory.getLogger(getClass());

	private CsvReader reader = new CsvReader();

	@Test
	public void tryCatchVavr() {
		System.out.println(callFunction());

	}

	@Test
	public void tryCatch() {
		System.out.println(standardTry());
	}

	private int callFunction() {
		return Try.of(() -> intWithException(true))
				.onFailure(e -> log.error("exception", e))
				.andFinally(() -> System.out.println("in Finally"))
				.get();
	}

	private int standardTry() {
		try {
			return intWithException(true);
		} catch (Exception e) {
			log.error("exception", e);
			return -1;
		} finally {
			System.out.println("in Finally");
		}
	}

	private int intWithException(boolean throwEx)  {
		if(throwEx) {
			throw new RuntimeException("int exception");
		}
		return 1;
	}


}
