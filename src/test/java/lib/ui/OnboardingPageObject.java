package lib.ui;

import io.appium.java_client.AppiumDriver;

public class OnboardingPageObject extends MainPageObject {

    private static final String
            SKIP_ONBOARDING_BUTTON = "androidUIAutomator:new UiSelector().resourceId(\"org.wikipedia:id/fragment_onboarding_skip_button\")";
    public OnboardingPageObject(AppiumDriver driver){
        super(driver);
    }

    public void skipOnboarding(){
        this.waitForElementAndClick(SKIP_ONBOARDING_BUTTON, "Кнопки 'Пропустить' нет", 90);
    }
}
