package com.example.demo;

/**
 * Created by Jakub Krhovják on 6/16/18.
 */
public class Kata {

	public static void main(String[] args) {
		System.out.println(run("Hey fellow warriors"));

	}

	public static String run(String string) {
		String result = "";
		for(String word : string.split(" ")) {
			if(word.length() > 5) {
				result += new StringBuilder(word).append(" ").reverse().toString();
			} else {
				result += word.concat(" ");
			}
		}
		return result;

//		return Stream.of(string.split(" ")).map(word -> {
//			if(word.length() > 5) {
//				return new StringBuilder(word).reverse().toString();
//			}			return word;
//		}).collect(Collectors.joining(" "));
	}
}
