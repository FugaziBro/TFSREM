package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

import org.junit.BeforeClass;
import org.junit.Test;
import pages.ExchangePage;

import static info.Configs.DELAY;
import static info.EndPoints.EXCHANGE_ENDPOINT;
import static info.Properties.*;


public class TestBase {

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

                main.table.waitUntil(Condition.visible, DELAY);
                ft.footer.waitUntil(Condition.visible, DELAY);
                hd.header.waitUntil(Condition.visible, DELAY);
                // hd.checkLinksAvailable();
                // ft.checkLinksAvailable();

                main.selectedArticleCheck();

                main.checkPresetCurrency();

                main.tableRowConvertation.get(2).should(Condition.text("€ → ₽"));

                main.ChangeValute(0,"Евро");
                main.currencySelectors.get(1).should(Condition.text("Рубль"));
                main.currencySelectors.get(0).should(Condition.text("Евро"));

                main.ChangeValute(1,"Доллар");

                main.currencySelectors.get(1).should(Condition.text("Доллар"));

                main.tableRowConvertation.get(2).should(Condition.text("€ → $"));
        }

}
