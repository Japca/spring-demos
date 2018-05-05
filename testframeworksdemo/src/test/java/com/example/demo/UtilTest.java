package com.example.demo;

import com.example.demo.domain.Child;
import com.example.demo.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.reflections.Reflections;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Jakub Krhovják on 5/5/18.
 */

@Data
@ToString
@AllArgsConstructor
class Item implements Serializable {

	private String name;
	private Record record;
}

@Data
@ToString
@AllArgsConstructor
class Record implements Serializable {
	private String name;
	private int age;
}

@Slf4j
public class UtilTest {

	@Test
	public void utilTests() {
		log.info("Subtypes: {}", new Reflections("com.example.demo.domain").getSubTypesOf(Person.class));
		assertThat(new Reflections("com.example.demo.domain").getSubTypesOf(Person.class)).contains(Child.class);

	}

	@Test
	public void copyTest() {
		Item item1 = new Item("Item1", new Record("Record1", 1));
		// use apache commons!
		Item item2 = SerializationUtils.roundtrip(item1);

		item2.setName("Item1 copy");
		item2.getRecord().setName("Record1 copy");
		item2.getRecord().setAge(2);

		log.info("{}", item1);
		log.info("{}", item2);

	}
}
