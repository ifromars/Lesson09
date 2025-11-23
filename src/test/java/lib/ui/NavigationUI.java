package lib.ui;


import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject{

    protected static String
    SAVED_BUTTON,
    GO_BACK_BUTTON,
    OPEN_NAVIGATION,
    MAIN_PAGE;

    public NavigationUI (RemoteWebDriver driver){
        super (driver);
    }
    @Step("Нажатие кнопки 'Назад'")
    public void goBackButtonClick(){
        if(Platform.getInstance().isMW()){
            System.out.println("Метод не используется в платформе " + Platform.getInstance().getPlatformVar());
        }else{
        this.waitForElementAndClick(GO_BACK_BUTTON,
                "Элемент 'Перейти вверх' не найден",
                15
        );
        }
    }
    @Step("Переход на главную страницу")
    public void navigateToMainPage(){
        this.waitForElementAndClick(MAIN_PAGE,
                "Элемент 'Поиск' не найден",
                15
        );
    }
    @Step("Нажатие на 'Сохраненные'")
    public void clickSaved(){
        if(Platform.getInstance().isMW()){
            tryClickElementWithFewAttempts(SAVED_BUTTON,
                    "Элемент 'Сохраненные' не найден",
                    10);
        }else{
        this.waitForElementAndClick(SAVED_BUTTON,
                "Элемент 'Сохраненные' не найден",
                90
        );
        }
    }

    @Step("Открытие навигации")
    public void openNavigation(){
        if(Platform.getInstance().isMW()){
            this.waitForElementAndClick(OPEN_NAVIGATION, "navigation is not found",7);
        }else{
            System.out.println("Метод не используется в платформе " + Platform.getInstance().getPlatformVar());
        }
    }
}
