package com.example.demo;

import com.example.demo.domain.Person;
import com.example.demo.service.CsvReader;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by Jakub krhovják on 11/15/17.
 */
public class CollectorTest extends AbstractTest {

	private CsvReader reader = new CsvReader();

	List<Person> data;

	@Override
	@Before
	public void setUp() throws Exception {
		data = reader.getList();
	}


	@Test
	public void firstTest() throws Exception {
		Map<Integer, Long> collect = data.stream()
				.collect(Collectors.groupingBy(Person::getYear, Collectors.counting()));

		Map.Entry<Integer, Long> min = data.stream()
				.collect(Collectors.groupingBy(Person::getYear, Collectors.counting()))
				.entrySet().stream()
				.min(Comparator.comparing(Map.Entry::getValue))
				.get();

		Map.Entry<Long, List<Map.Entry<Integer, Long>>> allMin = data.stream()
				.collect(Collectors.groupingBy(Person::getYear, Collectors.counting()))
				.entrySet().stream()
				.collect(Collectors.groupingBy(Map.Entry::getValue))
				.entrySet().stream()
				.min(Comparator.comparing(Map.Entry::getKey))
				.get();

		System.out.println("data = " + collect);
		System.out.println("min = " + min);
		System.out.println("allMin = " + allMin);

	}

	@Test
	public void dropAndTakeTest() throws Exception {

		List<Person> collect = data.parallelStream()
				//	.map(Person::getYear)
				.sorted(Comparator.comparingInt(Person::getYear))
				.dropWhile(person -> person.getYear() < 1959)
				.takeWhile(person -> person.getYear() < 1969)
				.distinct()
				.collect(toList());
		//collect.sort(Comparator.comparingInt(Person::getYear).reversed());
		//		.sort(Comparator.comparingInt(Person::getYear));
		System.out.println("collect = " + collect);
	}

	@Test
	public void math() throws Exception {
		boolean anyMatch = data.stream()
				.anyMatch(person -> person.getYear().equals(1968));
		System.out.println("anyMatch = " + anyMatch);

		boolean allMatch = data.stream()
				.allMatch(person -> person.getYear().equals(1968));
		System.out.println("allMatch = " + allMatch);

		boolean noneMatch = data.stream()
				.noneMatch(person -> person.getYear().equals(1900));
		System.out.println("allMatch = " + noneMatch);
	}

	@Test
	public void find() throws Exception {
		Optional<Person> any = data.stream()
				//	.map(Person::getYear)
				.sorted(Comparator.comparingInt(Person::getYear))
				.findAny();
		System.out.println("any = " + any);

		Optional<Person> first = data.stream()
				.sorted(Comparator.comparingInt(Person::getYear))
				.findFirst();
		System.out.println("first = " + first);

		data.stream()
				.filter(person -> person.getYear() == 1968)
				.forEach(System.out::println);


	}

	@Test
	public void reduce() throws Exception {

		Integer reduce = data.stream()
				.map(Person::getYear)
				.reduce((year1, year2) -> year1 + year2)
				.get();
		System.out.println("reduce = " + reduce);

		Integer collectSum = data.stream()
				.map(Person::getYear)
				.collect(Collectors.summingInt(year -> year));
		System.out.println("collectSum = " + collectSum);


		Integer collectSum2 = data.stream()
				.mapToInt(Person::getYear)
				.sum();
		System.out.println("collectSum2 = " + collectSum2);
	}

	@Test
	public void coll() throws Exception {
		List<Integer> collect = data.stream()
				.collect(Collectors.mapping(Person::getYear, toList()));
		String collectAndThen = data.stream()
				.collect(
						Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Person::getYear)),
								(Optional<Person> person) -> person.isPresent() ? person.get().getLastName() : "none"));
		System.out.println("collect = " + collectAndThen);
	}

	@Test
	public void applyPeek() throws Exception {
		List<Integer> apply =
				data.stream()
						.map(Person::getYear)
						.sorted()
						.map(year -> year + 1000)
						.collect(toList());
		System.out.println("apply = " + apply);

		List<Person> peekPerson = data.stream()
				.sorted(Comparator.comparingInt(Person::getYear))
				.dropWhile(person -> person.getYear() < 1959)
				.takeWhile(person -> person.getYear() < 1969)
				.peek(person -> person.setYear(person.getYear() + 1000))
				.collect(toList());
		System.out.println("applyPerson = " + peekPerson);

	}

	@Test
	public void r() throws Exception {
		final Map<Boolean, List<Person>> collect = data.stream()
				.collect(Collectors.partitioningBy(person -> person.getYear() < 1970));
		System.out.println("collect = " + collect);

	}
}



