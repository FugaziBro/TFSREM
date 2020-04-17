package steps;

import com.codeborne.selenide.Condition;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.ExchangePage;

import java.text.ParseException;

import static info.Configs.DELAY;
import static info.EndPoints.EXCHANGE_ENDPOINT;
import static info.Properties.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UiSteps {
    ExchangePage main = new ExchangePage();
    ExchangePage.Footer ft = new ExchangePage().new Footer();
    ExchangePage.Header hd = new ExchangePage().new Header();
    protected static Double EurCourse;
    protected static Double UsdCourse;

    @Given("tinkoff exchange page is open, page is loaded, and links are available")
    public void tinkoffExchangePageIsOpenPageIsLoadedAndLinksAreAvailable() {
        main.openURL(TINKOFF_URI,EXCHANGE_ENDPOINT);
        main.returnTable().waitUntil(Condition.visible, DELAY);
        ft.footer.waitUntil(Condition.visible, DELAY);
        hd.header.waitUntil(Condition.visible, DELAY);
        hd.checkLinksAvailable();
        ft.checkLinksAvailable();
    }

    @Then("check that exchange article is selected")
    public void checkThatExchangeArticleIsSelected() {
        main.selectedArticleCheck();
    }

    @Then("selectors should contain ruble and euro currencies and table row their convertation text")
    public void selectorsShouldContainRubleAndEuroCurrenciesAndTableRowTheirConvertationText() {
        main.returnSelectors().get(0).shouldBe(Condition.text(RUB));
        main.returnSelectors().get(1).shouldBe(Condition.text(EUR));
        main.returnTableRow().get(2).should(Condition.text(EUR_TO_RUB));
    }

    @When("change ruble to euro in first selector")
    public void changeRubleToEuroInFirstSelector() {
        main.ChangeValute(0,EUR);
    }

    @Then("selectors value should change")
    public void selectorsValueShouldChange() {
        main.returnSelectors().get(1).should(Condition.text(RUB));
        main.returnSelectors().get(0).should(Condition.text(EUR));
    }

    @When("i change ruble to dollar in second selector")
    public void iChangeRubleToDollarInSecondSelector() {
        main.ChangeValute(1,DOL);
    }

    @Then("selectors should contain euro and dollar and table row their convertation text")
    public void selectorsShouldContainEuroAndDollarAndTableRowTheirConvertationText() {
        main.returnSelectors().get(1).should(Condition.text(DOL));
        main.returnTableRow().get(2).should(Condition.text(EUR_TO_DOL));
    }

    @When("tinkoff is open")
    public void tinkoffIsOpen() {
        main.openURL(TINKOFF_URI,EXCHANGE_ENDPOINT);
    }

    @Then("eur course from CBR should be almost equal to tinkoff's course")
    public void eurCourseFromCBRShouldBeAlmostEqualToTinkoffSCourse() throws ParseException {
        assertThat(main.returnValueInTable(0) >= EurCourse
                &&
                EurCourse >= main.returnValueInTable(1)).isTrue();
    }

    @When("i change ruble to dollar")
    public void iChangeRubleOnDollar() {
        main.ChangeValute(0,DOL);
    }

    @Then("usd to euro couses should be equal too")
    public void usdToEuroCousesShouldBeEqualToo() throws ParseException {
        main.returnTableRow().get(2).should(Condition.text(EUR_TO_DOL));
        assertThat(main.returnValueInTable(0)
                >= Math.ceil(EurCourse/UsdCourse*100)/ 100
                &&
                Math.ceil(EurCourse/UsdCourse
                        *100)/ 100 >= main.returnValueInTable(1)).isTrue();
    }
}
