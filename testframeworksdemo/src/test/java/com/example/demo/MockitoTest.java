package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Jakub Krhovják on 5/5/18.
 */

class A {

	public String someMethod(String a){
		return a;
	}
}

final class B {
	public int foo(String s) {
		return Integer.parseInt(s);
	}
}


@Slf4j
public class MockitoTest {
	static  Logger logger = LoggerFactory.getLogger(Stopwatch.class);
	List mockedList;

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@Rule
	public Stopwatch stopwatch = new Stopwatch() {


		private void logInfo(Description description, String status, long nanos) {
         String testName = description.getMethodName();
        logger.info(String.format(testName, status, TimeUnit.NANOSECONDS.toMicros(nanos)));
      }


		@Override
		protected void succeeded(long nanos, Description description) {
			logInfo(description, "succeeded", nanos);
		}

		@Override
		protected void failed(long nanos, Throwable e, Description description) {
			logInfo(description, "failed", nanos);
		}

		@Override
		protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
			logInfo(description, "skipped", nanos);
		}

		@Override
		protected void finished(long nanos, Description description) {
			logInfo(description, "finished", nanos);
		}
	};



	@Mock
	private A a;

	@Before
	public void setUp() throws Exception {
		mockedList = mock(LinkedList.class);
	}

	@Test
	public void timesTest() {
		mockedList.add("once");

		mockedList.add("twice");
		mockedList.add("twice");

		mockedList.add("three times");
		mockedList.add("three times");
		mockedList.add("three times");

		//following two verifications work exactly the same - times(1) is used by default
		verify(mockedList).add("once");
		verify(mockedList, times(1)).add("once");

		//exact number of invocations verification
		verify(mockedList, times(2)).add("twice");
		verify(mockedList, times(3)).add("three times");

		//verification using never(). never() is an alias to times(0)
		verify(mockedList, never()).add("never happened");

		//verification using atLeast()/atMost()
		verify(mockedList, atLeastOnce()).add("three times");
		verify(mockedList, atLeast(2)).add("three times");
		verify(mockedList, atMost(5)).add("three times");


	}

	@Test
	public void verify2() {
	    List singleMock = mock(List.class);

		//using a single mock
		singleMock.add("was added first");
		singleMock.add("was added second");

		//create an inOrder verifier for a single mock
		InOrder inOrder = inOrder(singleMock);

		//following will make sure that add is first called with "was added first, then with "was added second"
		inOrder.verify(singleMock).add("was added first");
		inOrder.verify(singleMock).add("was added second");

		// B. Multiple mocks that must be used in a particular order
		List firstMock = mock(List.class);
		List secondMock = mock(List.class);

		//using mocks
		firstMock.add("was called first");
		secondMock.add("was called second");

		singleMock.add("one");

		//ordinary verification
		verify(singleMock).add("one");

		//verify that method was never called on a mock
		verify(singleMock, never()).add("two");

		A a = mock(A.class);
		when(a.someMethod("some arg"))
			//	.thenThrow(new RuntimeException())
				.thenReturn("foo");

		//First call: throws runtime exception:
		a.someMethod("some arg");

		//Second call: prints "foo"
		System.out.println(a.someMethod("some arg"));

		//Any consecutive call: prints "foo" as well (last stubbing wins).
		System.out.println(a.someMethod("some arg"));


	}

	@Test
	public void answer() {
		A a = mock(A.class);
		when(a.someMethod(anyString())).thenAnswer((Answer) invocation -> {
			Object[] args = invocation.getArguments();
			Object mock = invocation.getMock();
			return "called with arguments: " + args;
		});

		//the following prints "called with arguments: foo"
		System.out.println(a.someMethod("foo"));
	}

	@Mock
	private B b;

	@Test
	public void finalMockTest() {
		when(b.foo("1")).thenReturn(2);
		assertThat(b.foo("1")).isEqualTo(2);
	}
}
