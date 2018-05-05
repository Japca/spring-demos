package com.example.demo;

import com.example.demo.domain.Film;
import com.example.demo.exception.BadException;
import com.example.demo.exception.GoodException;
import com.example.demo.service.FilmPlayer;
import com.google.code.tempusfugit.concurrency.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by Jakub krhovják on 11/11/17.
 */

class Sleep {

	static void sleep(int sleepInSecond) {
		try {
			TimeUnit.SECONDS.sleep(sleepInSecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

public class CompleteFutureTest extends AbstractTest {

	private Logger log = LoggerFactory.getLogger(getClass());


	List<Film> films = List.of(new Film("Dead Alien", 2000L),
			new Film("In the middle", 700L),
			new Film("short hand", 500L)
	);

	private Film filmToWatch1500 = new Film("test1500", 1500L);
	private Film filmToWatch1000 = new Film("test1000", 1000L);
	private Film filmToWatch1300 = new Film("test1300", 1300L);

	private FilmPlayer filmPlayer = new FilmPlayer();

	@Test
	public void simple() throws Exception {
		List<CompletableFuture<Film>> futures = films.stream()
				.map(film -> CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(film)))
				.collect(toList());

		List<Film> times = futures.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList());


	}


	@Test
	public void oneFilm() throws Exception {
		Film filmToWatch = new Film("test", 1500L);
		System.out.println(filmToWatch);
		CompletableFuture future = CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(filmToWatch))
				.thenApply(film -> filmPlayer.watchFilm(filmToWatch))
				.thenAccept(film -> System.out.println(film.getLength()));


		Thread.sleep(1000L);
		System.out.println("END !");
		future.join();
	}

	@Test
	public void twoFilm() throws Exception {
		Film filmToWatch = new Film("test1500", 1500L);
		Film filmToWatch2 = new Film("test1000", 1000L);

		CompletableFuture<Film> future1 = CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(filmToWatch));
		CompletableFuture<Film> future2 = CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(filmToWatch2));

		future1.acceptEither(future2, Film::increasePlayed);
//

//
//		List.of(future1, future2).stream()
//				.map(CompletableFuture::join)
//				.forEach(System.out::println);


		//Thread.sleep(2000L);

		System.out.println(future2.get());
		System.out.println("END !");

	}

	@Test
	public void AnyOffComplete() throws Exception {
		Film filmToWatch = new Film("test1500", 1500L);
		Film filmToWatch2 = new Film("test1000", 1000L);
		Film filmToWatch3 = new Film("test1300", 1300L);

		long start = System.nanoTime();
		CompletableFuture<Film> future1 = CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(filmToWatch));
		CompletableFuture<Film> future2 = CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(filmToWatch2), new ForkJoinPool(4));


		future1.thenAcceptBoth(future2, (film1, film2) -> {
			film1.increasePlayed();
			film2.decreasePlayed();

		});
		//future2.acceptEither(future1, Film::increasePlayed);
		//future1.complete(filmToWatch);
		//	future1.complete(filmToWatch3);
//		CompletableFuture.allOf(future1, future2);
//		future1.complete(filmToWatch2);


		System.out.println("before in " + (System.nanoTime() - start) / 1_000_000);


		Thread.sleep(1200L);
		System.out.println(CompletableFuture.anyOf(future1, future2).get());
//		System.out.println(future2.get());
//		System.out.println(future1.get());
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("END in " + (System.nanoTime() - start) / 1_000_000);

	}

	private boolean doWork(CompletableFuture<Film> future) throws ExecutionException, InterruptedException {
		return future.complete(future.get());
	}

	@Test
	public void AnyOffCompleteStream() throws Exception {
		Film filmToWatch = new Film("test1500", 1500L);
		Film filmToWatch2 = new Film("test1000", 1000L);
		Film filmToWatch3 = new Film("test1300", 1300L);

		long start = System.nanoTime();
		CompletableFuture<Film> future1 = CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(filmToWatch));
		CompletableFuture<Film> future2 = CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(filmToWatch2));

		future2.acceptEither(future1, Film::increasePlayed);

		//future1.acceptEither(future2, Film::increasePlayed);
		//future1.complete(filmToWatch);
		future1.complete(filmToWatch3);
		//		CompletableFuture.allOf(future1, future2);
		//		future1.complete(filmToWatch2);
		File file = new File("");


		System.out.println("before in " + (System.nanoTime() - start) / 1_000_000);

		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("END in " + (System.nanoTime() - start) / 1_000_000);

		//Thread.sleep(1600L);
		System.out.println(CompletableFuture.anyOf(future1, future2).get());
		System.out.println(future2.get());
		System.out.println(future1.get());
		System.out.println("END !");

	}


	@Test
	public void exeptionChanel() throws Exception {
		Film filmToWatch = new Film("test1500", 1500L);
		Film filmToWatch2 = new Film("test1000", 1000L);
		Film filmToWatch3 = new Film("test1300", 1300L);
		//	CompletableFuture<Film> future1 = CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(filmToWatch));


		CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(filmToWatch2, true))
				.thenApply(film -> film.increasePlayed())
				.exceptionally(ex -> handleGoodException(ex));


		Thread.sleep(1600L);
		System.out.println(filmToWatch2);
		System.out.println("END !");


	}

	private int handleGoodException(Throwable ex) {
		if (ex instanceof GoodException) {
			return ((GoodException) ex).getFallbackValue().getPlayed();
		}
		throw new BadException("End of strory!");

	}

	private Film getFilm() {
		Film filmToWatch2 = new Film("test1000", 1000L);
		return filmToWatch2;
	}

	@Test
	public void combine() throws Exception {

		CompletableFuture<Film> firstFuture = CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(filmToWatch1500));
		CompletableFuture<Film> secondFuture = CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(filmToWatch1000));

		secondFuture.thenCombine(firstFuture, (secondFilm, firstFilm) -> {
			secondFilm.increasePlayed();
			System.out.println("In callback");
			return secondFilm;
		}).thenAccept(Film::increasePlayed).get();

//		/CompletableFuture.allOf(firstFuture, secondFuture).join();
		System.out.println(secondFuture.get());
		System.out.println(firstFuture.get());

	}

	@Slf4j
	static class SimpleTask implements Supplier<Integer> {

		private final int value;


		public SimpleTask(int value) {
			this.value = value;
		}

		@Override
		public Integer get() {
			log.info("Counting result...");
			Sleep.sleep(value);
			return value;
		}
	}

	@Slf4j
	static class Mutliplier implements Function<Integer, Integer> {

		@Override
		public Integer apply(Integer integer) {
			log.info("Multiplying  result...");
			Sleep.sleep(integer);
			return integer * integer;
		}
	}

	static class TestThreadFactory extends DefaultThreadFactory {

		AtomicInteger value = new AtomicInteger();

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "ultiplier thread-" + value.incrementAndGet());
		}
	}

	@Test
	public void thenApplyTest() throws ExecutionException, InterruptedException {

		ExecutorService service = Executors.newCachedThreadPool(r -> new Thread(r, "Multiplier thread"));


		CompletableFuture.supplyAsync(new SimpleTask(2))
				.thenApplyAsync(new Mutliplier(), service)
				.thenAccept(result -> log.info(" Result: {}", result))
				.join();
	}

	@Test
	public void threadNamingTest() {
		ExecutorService service = Executors.newCachedThreadPool(new TestThreadFactory());
		service.execute(() -> log.info("ahoj"));
		Sleep.sleep(1);
		service.execute(() -> {
			log.info("ahoj");
			Sleep.sleep(1);
		});
		service.execute(() -> log.info("ahoj"));
		Sleep.sleep(2);
		service.execute(() -> log.info("ahoj"));
	}
}
