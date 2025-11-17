package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import lib.ui.OnboardingPageObject;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class CoreTestCase extends TestCase {


    protected RemoteWebDriver driver;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        this.rotateScreenPortrait();
        this.skipWelcomeForiOSApp();
        this.openWikiWebPageForMobileWeb();

    }

    // Добавил что бы приложение закрывалось после теста
    @Override
    protected void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
        super.tearDown();
    }

    protected void rotateScreenPortrait() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            if (Platform.getInstance().isAndroid()) {
            ((AndroidDriver) driver).rotate(ScreenOrientation.PORTRAIT);
        } else if (Platform.getInstance().isiOS()) {
            ((IOSDriver) driver).rotate(ScreenOrientation.PORTRAIT);
            }
        } else {
            System.out.println("Метод не используется в платформе " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void rotateScreenLandscape() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            if (Platform.getInstance().isAndroid()) {
            ((AndroidDriver) driver).rotate(ScreenOrientation.LANDSCAPE);
        } else if (Platform.getInstance().isiOS()) {
            ((IOSDriver) driver).rotate(ScreenOrientation.LANDSCAPE);
            }
        } else {
            System.out.println("Метод не используется в платформе " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void sendAppToBack(int duration) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            if (Platform.getInstance().isAndroid()) {
                ((AndroidDriver) driver).runAppInBackground(Duration.ofSeconds(duration));
            } else if (Platform.getInstance().isiOS()) {
                ((IOSDriver) driver).runAppInBackground(Duration.ofSeconds(duration));
            }
        } else {
            System.out.println("Метод не используется в платформе " + Platform.getInstance().getPlatformVar());
        }
    }

    private void skipWelcomeForiOSApp() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            if (Platform.getInstance().isiOS()) {
                WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
                welcomePageObject.clickSkip();
            } else if (Platform.getInstance().isAndroid()) {
                OnboardingPageObject onboardingPageObject = new OnboardingPageObject(driver);
                onboardingPageObject.skipOnboarding();
            }
        } else {
            System.out.println("Метод не используется в платформе " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void openWikiWebPageForMobileWeb(){
        if(Platform.getInstance().isMW()){
            driver.get("https://ru.wikipedia.org/");
        }else{
            System.out.println("Метод не используется в платформе " + Platform.getInstance().getPlatformVar());
        }
    }
}
