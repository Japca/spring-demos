package com.example.demo.service;

import com.example.demo.domain.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jakub krhovják on 11/12/17.
 */
public class FilmPlayer {

	private Logger log = LoggerFactory.getLogger(getClass());

	public Film watchFilm(Film film) {
		return watchFilm(film, false);
	}

	public Film watchFilm(Film film, boolean throwE) {
		log.info("Start watching film: {}", film.getName());
//		if(throwE = true) {
//			throw new BadException("Werry bad");
//		}

		try {
			Thread.sleep(film.getLength());
		} catch (InterruptedException e) {
			log.error("Nuclear attack alert!", e);
		}
		log.info("Film {} has watched for: {} ms", film.getName(), film.getLength());
		film.setWatched(true);
		return film;
	}


}
