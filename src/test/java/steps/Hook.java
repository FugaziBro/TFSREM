package steps;

import com.codeborne.selenide.Configuration;
import cucumber.api.java.Before;

import static info.Configs.DELAY;

public class Hook {

    @Before
    public void preset(){
        Configuration.browser = "chrome";
        Configuration.timeout = DELAY;
    }

}
