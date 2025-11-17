package lib.ui;

import io.appium.java_client.AppiumBy;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver){
        this.driver=driver;
    }
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

    public void swipeUpQuick(){
        swipeUp(200);
    }

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

    public WebElement waitForElementPresent(String locator, String error_message, int timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(error_message + "\n");

        return wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.presenceOfElementLocated(by)
        ));
    }

    public WebElement waitForElementPresentZeroWait(String locator, String error_message){
        return waitForElementPresent (locator,error_message,0);
    }

    public WebElement waitForElementPresent(String locator, String error_message){
        return waitForElementPresent (locator,error_message,90);
    }

    public void assertElementHasText(String locator, String expected_text, String error_message) {
        WebElement element = waitForElementPresent(locator, error_message, 10);
        String actual_text = element.getText();
        Assert.assertEquals(error_message, expected_text, actual_text);

    }

    public WebElement waitForElementAndClick(String locator, String error_message, int timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, error_message,timeoutInSeconds);
        // Повторно находим элемент перед кликом, чтобы избежать StaleElementReferenceException
        element = waitForElementPresent(locator, error_message, 5);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, int timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        // Повторно находим элемент перед вводом текста, чтобы избежать StaleElementReferenceException
        element = waitForElementPresent(locator, error_message, 5);
        element.sendKeys(value);
        return element;
    }
    public boolean waitForElementNotPresent(String locator, String error_message, int timeoutInSeconds){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(error_message +"\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }
    public WebElement waitForElementAndClear(String locator, String error_message, int timeoutInSeconds){

        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;

    }

    public void assertAllElementsContainText(List<WebElement> elements, String text){
        for (WebElement element: elements){
            String actualText = element.getText();
            Assert.assertTrue("Элемент не содержит текст '" + text + "', текущий текст: " + actualText,
                    actualText.toLowerCase().contains(text.toLowerCase()));
        }
    }
    public int getAmountOfElements(String locator){
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator, String errorMessage){
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0){
            String defaultMessage = "An Element " + locator + " supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, int timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }



    public void swipeUpTillElementAppears(String locator, String errorMessage, int maxSwipes){

        int alreadySwiped = 0;
        while (!this.isElementLocatedOnTheScreen(locator)){
            if(alreadySwiped > maxSwipes){
                Assert.assertTrue(errorMessage, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

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

    public boolean isElementPresent(String locator){
        return getAmountOfElements(locator)>0;
    }

    public void scrollWebPageUp(){
        if(Platform.getInstance().isMW()){
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0,250)");
        } else{
            System.out.println("Метод не используется в платформе " + Platform.getInstance().getPlatformVar());
        }
    }

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

    public void scrollWebPageTillElementNotVisible(String locator, String errorMessage, int maxSwipes){
        int alreadySwiped = 0;

        WebElement element = this.waitForElementPresent(locator, errorMessage);
        while(!this.isElementLocatedOnTheScreen(locator)){
            scrollWebPageUp();
            ++alreadySwiped;
            if(alreadySwiped>maxSwipes){
                Assert.assertTrue(errorMessage, element.isDisplayed());
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
}
