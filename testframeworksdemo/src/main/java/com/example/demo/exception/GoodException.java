package com.example.demo.exception;

import com.example.demo.domain.Film;

/**
 * Created by Jakub krhovják on 11/15/17.
 */
public class GoodException extends RuntimeException {

	private Film fallbackValue;

	public GoodException(String message, Film fallbackValue) {
		super(message);
	}

	public Film getFallbackValue() {
		return fallbackValue;
	}

	public void setFallbackValue(Film fallbackValue) {
		this.fallbackValue = fallbackValue;
	}
}
