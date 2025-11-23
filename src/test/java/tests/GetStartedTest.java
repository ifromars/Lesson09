package tests;

import io.qameta.allure.*;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Тесты приветственного экрана")
public class GetStartedTest extends CoreTestCase {

    @Test
    @Features(value = {@Feature("Приветственный экран")})
    @DisplayName("Прохождение приветственного экрана")
    @Description("Прохождение всех экранов приветствия на iOS")
    @Severity(SeverityLevel.MINOR)
    public void testPassThroughWelcome(){

        if(Platform.getInstance().isAndroid() || Platform.getInstance().isMW()){
            return;
        }
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);

        WelcomePage.waitForLearnMoreLink();
        WelcomePage.clickNextButton();

        WelcomePage.waitForNewWaysToExplore();
        WelcomePage.clickNextButton();

        WelcomePage.waitForAddOrEditPreferredLanguages();
        WelcomePage.clickNextButton();

        WelcomePage.waitForPolicyText();
        WelcomePage.clickGetStartedButton();
        WelcomePage.clickGotIt();

    }
}
