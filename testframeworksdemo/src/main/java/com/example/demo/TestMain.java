package com.example.demo;

import com.example.demo.domain.Film;
import com.example.demo.service.FilmPlayer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Jakub krhovják on 11/12/17.
 */
@Slf4j
public class TestMain {
	private static FilmPlayer filmPlayer = new FilmPlayer();

	public static void main(String[] args) throws InterruptedException {
		Film filmToWatch = new Film("test", 1500L);
		System.out.println(filmToWatch);
		CompletableFuture.supplyAsync(() -> filmPlayer.watchFilm(filmToWatch))
				.thenApply(film -> filmPlayer.watchFilm(filmToWatch))
				.thenAccept(System.out::println);
		Thread.sleep(1000L);
		System.out.println("END !");
	}
}
