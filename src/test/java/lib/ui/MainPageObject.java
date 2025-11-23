package lib.ui;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lib.Platform;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver){
        this.driver=driver;
    }
    @Step("Свайп элемента влево")
    public void swipeElementToLeft(String locator, String error_message){
        WebElement element = waitForElementPresent(locator, error_message, 15);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y+lower_y)/2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Point start = new Point(right_x,middle_y);

        Point end;
        if(Platform.getInstance().isAndroid()){
            end = new Point(left_x, middle_y);
        }else{
            int offset_x =(-1 * element.getSize().getWidth());
            end = new Point(offset_x, middle_y);
        }

        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), start.getX(), start.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                PointerInput.Origin.viewport(), end.getX(), end.getY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
    }

    @Step("Свайп вверх")
    public void swipeUp (int timeOfSwipe){

        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int)(size.height * 0.8);
        int end_y = (int)(size.height * 0.2);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Point start = new Point(x,start_y);
        Point end = new Point(x, end_y);
        Sequence swipe = new Sequence( finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), start.getX(), start.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe), PointerInput.Origin.viewport(), end.getX(), end.getY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));

    }

    @Step("Быстрый свайп вверх")
    public void swipeUpQuick(){
        swipeUp(200);
    }

    @Step("Свайп вверх до появления элемента")
    public void swipeUpToFindElement(String locator, String error_message, int maxSwipes){
        By by = this.getLocatorByString(locator);
        int alreadySwiped = 0;
        while (driver.findElements(by).isEmpty()){
            if(alreadySwiped > maxSwipes){
                waitForElementPresent(locator, "Элемент не найден при свайпе. \n" + error_message, 0 );
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    @Step("Ожидание появления элементов")
    public List<WebElement> waitForElementsPresent(By by, String error_message, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }
//    public WebElement waitForElementPresent(String locator, String error_message, int timeoutInSeconds){
//        By by = this.getLocatorByString(locator);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
//        wait.withMessage(error_message + "\n");
//        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
//    }

    @Step("Ожидание появления элемента")
    public WebElement waitForElementPresent(String locator, String error_message, int timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(error_message + "\n");

        return wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.presenceOfElementLocated(by)
        ));
    }

    @Step("Ожидание появления элемента (без ожидания)")
    public WebElement waitForElementPresentZeroWait(String locator, String error_message){
        return waitForElementPresent (locator,error_message,0);
    }

    @Step("Ожидание появления элемента")
    public WebElement waitForElementPresent(String locator, String error_message){
        return waitForElementPresent (locator,error_message,90);
    }

    @Step("Проверка текста элемента")
    public void assertElementHasText(String locator, String expected_text, String error_message) {
        WebElement element = waitForElementPresent(locator, error_message, 10);
        String actual_text = element.getText();
        Assertions.assertEquals(expected_text, actual_text, error_message);

    }

    @Step("Ожидание и клик по элементу")
    public WebElement waitForElementAndClick(String locator, String error_message, int timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, error_message,timeoutInSeconds);
        // Повторно находим элемент перед кликом, чтобы избежать StaleElementReferenceException
        element = waitForElementPresent(locator, error_message, 5);
        element.click();
        return element;
    }

    @Step("Ожидание элемента и ввод текста '{value}'")
    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, int timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        // Повторно находим элемент перед вводом текста, чтобы избежать StaleElementReferenceException
        element = waitForElementPresent(locator, error_message, 5);
        element.sendKeys(value);
        return element;
    }
    @Step("Ожидание исчезновения элемента")
    public boolean waitForElementNotPresent(String locator, String error_message, int timeoutInSeconds){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(error_message +"\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }
    @Step("Ожидание элемента и очистка поля")
    public WebElement waitForElementAndClear(String locator, String error_message, int timeoutInSeconds){

        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;

    }

    @Step("Проверка что все элементы содержат текст '{text}'")
    public void assertAllElementsContainText(List<WebElement> elements, String text){
        for (WebElement element: elements){
            String actualText = element.getText();
            Assertions.assertTrue(actualText.toLowerCase().contains(text.toLowerCase()),
                    "Элемент не содержит текст '" + text + "', текущий текст: " + actualText);
        }
    }
    @Step("Получение количества элементов")
    public int getAmountOfElements(String locator){
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    @Step("Проверка отсутствия элемента")
    public void assertElementNotPresent(String locator, String errorMessage){
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0){
            String defaultMessage = "An Element " + locator + " supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    @Step("Ожидание элемента и получение атрибута '{attribute}'")
    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, int timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }



    @Step("Свайп вверх до появления элемента")
    public void swipeUpTillElementAppears(String locator, String errorMessage, int maxSwipes){

        int alreadySwiped = 0;
        while (!this.isElementLocatedOnTheScreen(locator)){
            if(alreadySwiped > maxSwipes){
                Assertions.assertTrue(this.isElementLocatedOnTheScreen(locator), errorMessage);
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    @Step("Проверка расположения элемента на экране")
    public boolean isElementLocatedOnTheScreen(String locator){
        int elementLocationByY = this.waitForElementPresent(locator,"Footer не найден",90).getLocation().getY();
        if(Platform.getInstance().isMW()){
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object jsResult = JSExecutor.executeScript("return window.pageYOffset");
            elementLocationByY -= Integer.parseInt(jsResult.toString());
        }
        int screenSizeByY = driver.manage().window().getSize().getHeight();
        return elementLocationByY < screenSizeByY;
    }

    @Step("Проверка наличия элемента")
    public boolean isElementPresent(String locator){
        return getAmountOfElements(locator)>0;
    }

    @Step("Прокрутка веб-страницы вверх")
    public void scrollWebPageUp(){
        if(Platform.getInstance().isMW()){
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0,250)");
        } else{
            System.out.println("Метод не используется в платформе " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Попытка клика по элементу с несколькими попытками")
    public void tryClickElementWithFewAttempts(String locator, String errorMessage, int amountOfAttempts){

        int currentAttempt = 0;
        boolean needMoreAttempts = true;

        while(needMoreAttempts){
            try{
                this.waitForElementAndClick(locator, errorMessage, 1);
                needMoreAttempts = false;
            }
            catch (Exception e){
                if (currentAttempt > amountOfAttempts){
                    this.waitForElementAndClick(locator, errorMessage, 1);

                }
            }
            ++currentAttempt;
        }
    }

    @Step("Прокрутка веб-страницы до скрытия элемента")
    public void scrollWebPageTillElementNotVisible(String locator, String errorMessage, int maxSwipes){
        int alreadySwiped = 0;

        WebElement element = this.waitForElementPresent(locator, errorMessage);
        while(!this.isElementLocatedOnTheScreen(locator)){
            scrollWebPageUp();
            ++alreadySwiped;
            if(alreadySwiped>maxSwipes){
                Assertions.assertTrue(element.isDisplayed(), errorMessage);
            }

        }
    }

    private By getLocatorByString(String locatorWithType) {
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = explodedLocator[0];
        String byLocator = explodedLocator[1];
        if (byType.equals("xpath")) {
            return By.xpath(byLocator);
        } else if (byType.equals("id")) {
            return By.id(byLocator);
        } else if (byType.equals("androidUIAutomator")) {
            return AppiumBy.androidUIAutomator(byLocator);
        } else if (byType.equals("accessibilityId")) {
            return AppiumBy.accessibilityId(byLocator);
        }else if (byType.equals("iOSClassChain")){
            return AppiumBy.iOSClassChain(byLocator);
        }else if (byType.equals("css")){
        return By.cssSelector(byLocator);
    }else{
            throw new IllegalArgumentException("Локатор не найден");
        }
    }

    @Step("Создание скриншота '{name}'")
    public String takeScreenshot(String name){
        TakesScreenshot ts = (TakesScreenshot)this.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "_screenshot.png";
        try {
//            FileUtils.copyFile(source, new File(path));
            Files.copy(source.toPath(), new File(path).toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Скриншот сделан" + path);

        } catch (Exception e) {
            System.out.println("Невозможно сделать скриншот " + e.getMessage());
        }
        return path;
    }

    @Attachment
    public static byte[] screenshot(String path){
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Cannot get bytes from screenshot. Error " + e.getMessage());
        }
        return bytes;
    }
}
