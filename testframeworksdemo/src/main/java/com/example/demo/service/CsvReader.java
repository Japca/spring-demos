package com.example.demo.service;

import com.example.demo.domain.Person;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub krhovják on 11/16/17.
 */
public class CsvReader {

	private static final String COMMA = ",";

	public List<Person> getList() throws IOException {
		File file = new ClassPathResource("MOCK_DATA.csv").getFile();
		List<Person> data = new ArrayList<>();
		try (FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader)) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] csvData = line.split(COMMA);
				data.add(new Person(Long.parseLong(csvData[0]),
						csvData[1],
						csvData[2],
						csvData[3],
						Integer.parseInt(csvData[4])
				));

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}


}
