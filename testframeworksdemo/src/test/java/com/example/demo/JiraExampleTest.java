package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import static io.restassured.RestAssured.given;

/**
 * Created by Jakub krhovják  on 7/4/17.
 *
 */
public class JiraExampleTest extends  FrameworksDemoTest {

    @Value("${jiraHost}")
    private String jiraHost;

    @Value("${cookie}")
    private String cookie;

    @Test
    public void createIssue() {
        RestAssured.baseURI = jiraHost;
        create();
    }


    @Test
    public void addComment() throws Exception {
        RestAssured.baseURI= jiraHost;
        String id =  getIssueId();
        Response response = given().header("Content-Type", "application/json").
                header("Cookie", cookie).
                body("{ \"body\": \"New comment added!!!!\","+
                        "\"visibility\": {"+
                        "\"type\": \"role\","+
                        "\"value\": \"Administrators\" }"+
                        "}").when().
                post("/rest/api/2/issue/"+ id +"/comment/").then().statusCode(201).extract().response();
        System.out.println("------------------------------- "  + jsonPath(response).get("id"));


    }

    @Test
    public void updateComment() throws Exception {
        RestAssured.baseURI= jiraHost;
        String id = getIssueId();
        given().header("Content-Type", "application/json").
                header("Cookie", cookie).
                body("{ \"body\": \"Updated comment!\","+
                        "\"visibility\": {"+
                        "\"type\": \"role\","+
                        "\"value\": \"Administrators\" }"+
                        "}").when().
                put("/rest/api/2/issue/"+ id+ "/comment/" + id).
                then().
                statusCode(200);
    }

    private void create() {
        Response response = given().header("Content-Type", "application/json").
                header("Cookie",cookie).
                body("{"+
                        "\"fields\": {"+
                        "\"project\":{"+
                        "\"key\": \"TESTJIRA\""+
                        "},"+
                        "\"summary\": \"Jera test issue\","+
                        "\"description\": \"Creating my second bug\","+
                        "\"issuetype\": {"+
                        "\"name\": \"Bug\""+
                        "}"+
                        "}}").when().
                post("/rest/api/2/issue").
                then().
                statusCode(201).
                extract().
                response();

        JsonPath js= jsonPath(response);
        String id=js.get("id");
        System.out.println("------------------------------- "  + id);
    }

    private String getIssueId() {
        Response created = given().header("Content-Type", "application/json").
                header("Cookie", cookie).
                when().
                get("/rest/api/2/search?key=TESTJIRA").
                then().statusCode(200).extract().response();
        return jsonPath(created).get("issues[0].id");
    }
}
