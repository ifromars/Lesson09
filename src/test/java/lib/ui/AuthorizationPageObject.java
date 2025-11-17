package lib.ui;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject{
    private static final String
        LOGIN_BUTON ="xpath://div/a/span[text()=\"Войти\"]",
        LOGIN_INPUT="css:input.loginText",
        PASSWORD_INPUT="css:input.loginPassword",
        SUBMIT_BUTTON="css:button.mw-htmlform-submit";

    public AuthorizationPageObject(RemoteWebDriver driver){
        super(driver);
    }

    public void clickAuthButton(){
        this.waitForElementPresent(LOGIN_BUTON,"cannot find login",15);
        // Используем JavaScript клик для обхода проблем с интерактивностью
        WebElement element = this.waitForElementPresent(LOGIN_BUTON,"cannot find login",5);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    public void enterLoginData (String login, String password){
        this.waitForElementAndSendKeys(LOGIN_INPUT, login,"cannot enter login",5);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password,"cannot enter password",5);

    }
    public void submitForm(){
        this.waitForElementAndClick(SUBMIT_BUTTON,"cannot click submit",5);
    }
}
