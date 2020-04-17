package steps;

import apimodel.ApiDailyRequestModel;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static info.Configs.todayStr;
import static info.Configs.tomorrowStr;
import static info.EndPoints.CBR_ENDPOINT;
import static info.Properties.CBR_URI;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiSteps {
    private static RequestSpecification requestTemplate = RestAssured
            .given().baseUri(CBR_URI);
    private ApiDailyRequestModel apiModel;
    private Double eurCourse;
    private Double usdCourse;

    @Given("response has returned with status {int}, json content type, and json schema has passed")
    public void responseHasReturnedWithStatusJsonContentTypeAndJsonSchemaHasPassed(int status) {
        Response resp = requestTemplate
                .when().get(CBR_ENDPOINT);
        resp.then()
                .statusCode(status)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("schema.json"));
    }

    @Then("Date and TimeStamp should be correct dates, and response should have have eur and usd courses")
    public void dateAndTimeStampShouldBeCorrectDatesAndResponseShouldHaveHaveEurAndUsdCourses() {
        apiModel = requestTemplate
                .when()
                .get(CBR_ENDPOINT).as(ApiDailyRequestModel.class, ObjectMapperType.GSON);
        assertThat(apiModel.getDate().substring(0,10)).isEqualTo(tomorrowStr);
        assertThat(apiModel.getTimestamp().substring(0,10)).isEqualTo(todayStr);

        eurCourse = requestTemplate
                .when()
                .get(CBR_ENDPOINT).then().extract().body().jsonPath().getDouble("Valute.EUR.Value");
        UiSteps.EurCourse = eurCourse;
        usdCourse = requestTemplate
                .when()
                .get(CBR_ENDPOINT).then().extract().body().jsonPath().getDouble("Valute.USD.Value");
        UiSteps.UsdCourse = usdCourse;
    }
}
