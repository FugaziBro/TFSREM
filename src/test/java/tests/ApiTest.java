package tests;

import apimodel.ApiDailyRequestModel;
import com.codeborne.selenide.Condition;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.junit.BeforeClass;
import org.junit.Test;
import pages.ExchangePage;

import java.text.NumberFormat;
import java.text.ParseException;

import static com.codeborne.selenide.Selenide.open;
import static info.Configs.*;
import static info.EndPoints.CBR_ENDPOINT;
import static info.EndPoints.EXCHANGE_ENDPOINT;
import static info.Properties.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiTest {
    private static RequestSpecification requestTemplate;
    private ApiDailyRequestModel apiModel;
    private Double eurCourse;
    private Double usdCourse;
    private ExchangePage main = new ExchangePage();
    /*
    private EUR eur;
    private USD usd;
     */


    @BeforeClass
    public static void preset(){
        requestTemplate = RestAssured
                .given().baseUri(CBR_URI);
    }

    @Test
    public void ApiAssertTest(){
        Response resp = requestTemplate
                .when().get(CBR_ENDPOINT);
        resp.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("schema.json"));
    }
    @Test
    public void ApiDeserializeTest() throws ParseException {
        apiModel = requestTemplate
                .when()
                .get(CBR_ENDPOINT).as(ApiDailyRequestModel.class,ObjectMapperType.GSON);
        /*  Десереализует но заполняет все значения null'ами idk
            eur = requestTemplate.when().get(CBR_ENDPOINT).as(EUR.class,ObjectMapperType.GSON);
            usd = requestTemplate.when().get(CBR_ENDPOINT).as(USD.class,ObjectMapperType.GSON);
        */
        eurCourse = requestTemplate
                .when()
                .get(CBR_ENDPOINT).then().extract().body().jsonPath().getDouble("Valute.EUR.Value");
        usdCourse = requestTemplate
                .when()
                .get(CBR_ENDPOINT).then().extract().body().jsonPath().getDouble("Valute.USD.Value");

        //assertThat(apiModel.getDate().substring(0,10)).isEqualTo(tomorrowStr);
        //assertThat(apiModel.getTimestamp().substring(0,10)).isEqualTo(todayStr);

        open(TINKOFF_URI + EXCHANGE_ENDPOINT);

        assertThat(main.returnValueInTable(0) >= eurCourse
                &&
                eurCourse >= main.returnValueInTable(1)).isTrue();
        main.ChangeValute(0,"Доллар");
        main.returnTableRow().get(2).should(Condition.text(EUR_TO_DOL));
        assertThat(main.returnValueInTable(0) >= Math.ceil(eurCourse/usdCourse*100)/ 100
                &&
                Math.ceil(eurCourse/usdCourse*100)/ 100 >= main.returnValueInTable(1)).isTrue();
    }

}
