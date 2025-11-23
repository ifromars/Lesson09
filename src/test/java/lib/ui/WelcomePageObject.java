package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject{

    public WelcomePageObject (RemoteWebDriver driver){
        super(driver);
    }

    private static final String
        STEP_LEARN_MORE_LINK = "iOSClassChain:**/XCUIElementTypeStaticText[`name == \"Узнать подробнее о Википедии\"`]",
        STEP_WAIT_FOR_NEW_WAYS_TO_EXPLORE = "iOSClassChain:**/XCUIElementTypeStaticText[`name == \"Новые способы изучения\"`]",
        STEP_PREFERRED_LANGUAGE = "iOSClassChain:**/XCUIElementTypeStaticText[`name == \"Добавить или изменить предпочтительные языки\"`]",
        STEP_POLICY = "iOSClassChain:**/XCUIElementTypeStaticText[`name == \"Узнайте больше о нашей политике конфиденциальности и условиях использования.\"`]",
        STEP_NEXT = "iOSClassChain:**/XCUIElementTypeStaticText[`name == \"Далее\"`]",
        STEP_GET_STARTED = "iOSClassChain:**/XCUIElementTypeStaticText[`name == \"Начать\"`]",
        STEP_GOT_IT = "accessibilityId:Понятно",
        SKIP = "accessibilityId:Пропустить";

    @Step("Ожидание ссылки 'Узнать подробнее'")
    public void waitForLearnMoreLink(){
        this.waitForElementPresent(
                STEP_LEARN_MORE_LINK,
                "'Узнать подробнее о Википедии' не найден",
                90);
    }

    @Step("Ожидание текста 'Новые способы изучения'")
    public void waitForNewWaysToExplore(){
        this.waitForElementPresent(
                STEP_WAIT_FOR_NEW_WAYS_TO_EXPLORE,
                "'Новые способы изучения' не найден",
                10);
    }

    @Step("Ожидание текста 'Добавить или изменить предпочтительные языки'")
    public void waitForAddOrEditPreferredLanguages(){
        this.waitForElementPresent(
                STEP_PREFERRED_LANGUAGE,
                "'Добавить или изменить предпочтительные языки' не найден",
                10);
    }

    @Step("Ожидание текста политики конфиденциальности")
    public void waitForPolicyText(){
        this.waitForElementPresent(
                STEP_POLICY,
                "'Узнайте больше о нашей политике конфиденциальности и условиях использования.' не найден",
                10);
    }

    @Step("Нажатие кнопки 'Далее'")
    public void clickNextButton(){
        this.waitForElementAndClick(STEP_NEXT,
                "'Узнать подробнее о Википедии' не найден",
                10);
    }

    @Step("Нажатие кнопки 'Начать'")
    public void clickGetStartedButton(){
        this.waitForElementAndClick(STEP_GET_STARTED,
                "'Узнать подробнее о Википедии' не найден",
                10);
    }

    @Step("Нажатие кнопки 'Понятно'")
    public void clickGotIt(){
        this.waitForElementAndClick(STEP_GOT_IT,
                "'Понятно' не найден",
                10);
    }

    @Step("Нажатие кнопки 'Пропустить'")
    public void clickSkip(){
        this.waitForElementAndClick(SKIP, "Кнопки 'Пропустить' нет", 15);
    }
}
