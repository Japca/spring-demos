package com.example.demo;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.hasItems;

/**
 * Created by Jakub krhovják on 7/3/17.
 * <p>
 * Please comment when server.port  in application.properties when run ControllerTest
 */

public class ControllerTest extends FrameworksDemoTest {

	@Test
	public void controller() {
		given().
				param("dummy", "dummy").
				when().
				get("/data").
				then().log().all().
				statusCode(200).
				body("id", hasItems(1, 2)).
				body("name", hasItems("Item", "Data"));

	}

	@Test
	public void schema() {
		given().
				when().
				get("/schema").
				then().log().all().
				assertThat().body(matchesJsonSchemaInClasspath("data-schema.json"));
	}

//    @Test
//    public void async() throws Exception {
//        int wait = 5000;
//        () -> given().
//                when().asy
//                post("/async", 5000)
//                then().log().all().
//
//
//    }
}
