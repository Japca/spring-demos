package com.example.demo.domain;

/**
 * Created by Jakub krhovják on 11/16/17.
 */
public class Person {

	private Long id;

	private String firstName;

	private String lastName;

	private String country;

	private Integer year;

	public Person(Long id, String firstName, String lastName, String country, Integer year) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.year = year;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "\nPerson{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", country='" + country + '\'' + ", year="
				+ year + '}' + "\n";
	}
}
