package com.example.demo;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrameworksDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FrameworksDemoTest {

	protected static JsonPath jsonPath(Response response) {
		String localResponse = response.asString();
		return new io.restassured.path.json.JsonPath(localResponse);
	}
}
