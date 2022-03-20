package steps;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.logging.Logger;

public class GetTodos {
  private static final Logger logger = Logger.getLogger(GetTodos.class.getName());
  Response response;
  RequestSpecification requestSpecification = RestAssured.given();
  String BASE_URL = "https://gorest.co.in/public/v2/todos";


  @Test
  @Given("^I have hit API$")
  public void hitApi() {
    RestAssured.baseURI = BASE_URL;
    requestSpecification.header("Content-Type", "application/json");

    hitUrlProcess();

  }

  @Test
  @Then("^I want to make sure it shows only 20 items$")
  public void validateCount(){
    hitUrlProcess();
    List<String> resp = response.jsonPath().get();
    JsonArray jsonArray = new Gson().toJsonTree(resp).getAsJsonArray();
    Assert.assertEquals(20, jsonArray.size());

  }

  private String hitUrlProcess() {
    response = requestSpecification.get(BASE_URL);
    logger.info(":: RESPONSE :: " + response.getBody().asString());

    return response.getBody().asString();

  }

}
