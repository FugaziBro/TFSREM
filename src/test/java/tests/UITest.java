package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

import org.junit.BeforeClass;
import org.junit.Test;
import pages.ExchangePage;

import static info.Configs.DELAY;
import static info.EndPoints.EXCHANGE_ENDPOINT;
import static info.Properties.*;


public class UITest {

        ExchangePage main = new ExchangePage();
        ExchangePage.Footer ft = new ExchangePage().new Footer();
        ExchangePage.Header hd = new ExchangePage().new Header();

        @BeforeClass
        public static void  preset(){
                Configuration.browser = "chrome";
                Configuration.timeout = DELAY;
        }

        @Test
        public void tinkoffExchangeUITest(){
                main.openURL(TINKOFF_URI,EXCHANGE_ENDPOINT);

                main.returnTable().waitUntil(Condition.visible, DELAY);
                ft.footer.waitUntil(Condition.visible, DELAY);
                hd.header.waitUntil(Condition.visible, DELAY);
                hd.checkLinksAvailable();
                ft.checkLinksAvailable();

                main.selectedArticleCheck();

                main.returnSelectors().get(0).shouldBe(Condition.text(RUB));
                main.returnSelectors().get(1).shouldBe(Condition.text(EUR));

                main.returnTableRow().get(2).should(Condition.text(EUR_TO_RUB));

                main.ChangeValute(0,EUR);
                main.returnSelectors().get(1).should(Condition.text(RUB));
                main.returnSelectors().get(0).should(Condition.text(EUR));

                main.ChangeValute(1,DOL);

                main.returnSelectors().get(1).should(Condition.text(DOL));

                main.returnTableRow().get(2).should(Condition.text(EUR_TO_DOL));
        }

}
