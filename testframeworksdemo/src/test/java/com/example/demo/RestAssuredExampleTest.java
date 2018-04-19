package com.example.demo;

import com.example.demo.controller.DataController;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * ;
 *
 * @author Jakub Krhovják-fg8y7n
 * @since 7/3/2017
 */
public class RestAssuredExampleTest extends FrameworksDemoTest {

    @Autowired
    private DataController controller;

    @Value("${key}")
    private String apiKey;

    @Value("${host}")
    private String host;

    private static final String BODY =
            "{" +
                    "\"location\": {" +
                    "\"lat\": -33.8669710," +
                    "\"lng\": 151.1958750" +
                    "}," +
                    "\"accuracy\": 50," +
                    "\"name\": \"Google Shoes!\"," +
                    "\"phone_number\": \"(02) 9374 4000\"," +
                    "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\"," +
                    "\"types\": [\"shoe_store\"]," +
                    "\"website\": \"http://www.google.com.au/\"," +
                    "\"language\": \"en-AU\"" +
                    "}";

    @Test
    public void get() {
        RestAssured.baseURI = host;
        given().param("location", "-33.8670522,151.1957362").
                param("radius", "500").
                param("key", apiKey).
                when().
                get("/maps/api/place/nearbysearch/json").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("results[0].name", equalTo("Sydney")).and().
                body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and().
                header("Server", "pablo");
    }


    @Test
    public void post() {
        RestAssured.baseURI = host;
        given().queryParam("key", apiKey).
                body(BODY).
                when().
                post("/maps/api/place/add/json").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("status", equalTo("OK")).
                body("scope", equalTo("APP"));
    }


    @Test
    public void addAndDelete() {
        RestAssured.baseURI = host;
        Response res = given().queryParam("key", apiKey).
                body(BODY).
                when().
                post("/maps/api/place/add/json").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("status", equalTo("OK")).
                extract().response();
        String responseString = res.asString();
        System.out.println(responseString);
        JsonPath js = new JsonPath(responseString);
        String placeId = js.get("place_id");
        System.out.println(placeId);
        given().
                queryParam("key", apiKey).
                body("{" +
                        "\"place_id\": \"" + placeId + "\"" +
                        "}").
                when().
                post("/maps/api/place/delete/json").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("status", equalTo("OK"));
    }


}
