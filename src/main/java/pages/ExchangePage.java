package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.restassured.RestAssured;
import org.openqa.selenium.By;


import java.text.NumberFormat;
import java.text.ParseException;

import static com.codeborne.selenide.Selenide.*;
import static info.Configs.SELECTED_ARTICLE_COLOR;

public class ExchangePage {

    // Variables

    private SelenideElement table = $(By.className("DesktopExchange__tableRoot_1MoWP"));
    private SelenideElement selectedArticle = $(By.xpath("//span[text()='Курсы валют']"));
    private ElementsCollection tableRowConvertation = $$(By.className("DesktopExchange__th_AXtbR"));
    private ElementsCollection currencySelectors = $$(By.xpath("//div[@class='Select__valueWrapper_Ni7om']"));
    private ElementsCollection currencyValues = $$(By.xpath("//div[@class='Text__text_primary_28uo7']"));


    //Methods
    public ExchangePage openURL(String URI, String endpoint) {
        open(URI + endpoint);
        return page(ExchangePage.class);
    }

    public void selectedArticleCheck(){
        selectedArticle.should(Condition.cssValue("color", SELECTED_ARTICLE_COLOR));
    }



    public void ChangeValute(int selectorNum, String Currency){
        currencySelectors.get(selectorNum).click();
        $(By.xpath("//div[text()='"+Currency+"']")).click();
    }

    public Double returnValueInTable(int indexOfTable) throws ParseException {
        String strValue = currencyValues.get(indexOfTable).getText();
        return NumberFormat.getInstance().parse(strValue).doubleValue();
    }

    public ElementsCollection returnSelectors(){
        return currencySelectors;
    }
    public ElementsCollection returnTableRow(){
        return tableRowConvertation;
    }
    public SelenideElement returnTable(){
        return table;
    }



        public class Footer {
            //Variables
            public SelenideElement footer = $(By.className("footer__1pHys"));
            private ElementsCollection linksFooter = $$(By.xpath("//ul[@class='footer__3WXFx']//a"));

            //Methods
            public void checkLinksAvailable() {
                for (SelenideElement elem : linksFooter) {
                    String URL = elem.getAttribute("href");
                    RestAssured.given().baseUri(URL)
                            .when().get()
                            .then().assertThat().statusCode(200);
                }
            }
        }

        public class Header {
            //Variables
            public SelenideElement header = $(By.xpath("//div[@data-block-type='headerSlim']"));
            private ElementsCollection linksHeader = $$(By.xpath("//div[@data-block-type='headerSlim']//a"));

            //Methods
            public void checkLinksAvailable() {
                for (SelenideElement elem : linksHeader) {
                    String URL = elem.getAttribute("href");
                    RestAssured.given().baseUri(URL)
                            .when().get()
                            .then().assertThat().statusCode(200);
                }
            }
        }
}
