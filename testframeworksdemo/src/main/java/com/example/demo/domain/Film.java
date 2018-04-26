package com.example.demo.domain;

import com.example.demo.exception.BadException;
import com.example.demo.exception.GoodException;

/**
 * Created by Jakub krhovják on 11/12/17.
 */
public class Film {

	public Film(String name, Long length) {
		this.name = name;
		this.length = length;
	}

	private String name;

	private Long length;

	private boolean watched;

	private int played;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public boolean isWatched() {
		return watched;
	}

	public void setWatched(boolean watched) {
		this.watched = watched;
	}

	public int getPlayed() {
		return played;
	}

	public int increasePlayed() {
		return ++played;
	}

	public Film increasePlayedGoodExceptionally() {
		++played;
		GoodException exception = new GoodException("Good exception can continue", this);
		return this;

	}

	public int increasePlayedBadExceptionally() {

		throw new BadException("BAD exception STOP!!!");
	}


	public int decreasePlayed() {
		return --played;
	}

	@Override
	public String toString() {
		return "Film{" + "name='" + name + '\'' + ", length=" + length + ", watched=" + watched + ", played=" + played + '}';
	}
}
