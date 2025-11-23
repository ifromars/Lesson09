package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import lib.ui.OnboardingPageObject;
import lib.ui.WelcomePageObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.Properties;

public class CoreTestCase{


    protected RemoteWebDriver driver;


    @BeforeEach
    @Step("Запуск драйвера и сессии")
    public void setUp() throws Exception {
        driver = Platform.getInstance().getDriver();
        this.createAllurePropertyFile();
        this.rotateScreenPortrait();
        this.skipWelcomeForMobileApps();
        this.openWikiWebPageForMobileWeb();

    }

    // Добавил что бы приложение закрывалось после теста
    @AfterEach
    @Step("Конец драйвера и сессии")
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
    @Step("Выставляем портретную ориентацию экрана")
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
    @Step("Выставляем горизонтальную ориентацию экрана")
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
    @Step("Сворачиваем приложение (не используется в тестах с мобильным веб)")
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
    @Step("Пропуск вступительного экрана")
    private void skipWelcomeForMobileApps() {
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
    @Step("Открытие страницы википедия на мобильной версиии браузера")
    protected void openWikiWebPageForMobileWeb(){
        if(Platform.getInstance().isMW()){
            driver.get("https://ru.wikipedia.org/");
        }else{
            System.out.println("Метод не используется в платформе " + Platform.getInstance().getPlatformVar());
        }
    }

    private void createAllurePropertyFile(){
        String path = System.getProperty("allure.results.directory");
        try {
            Properties props = new Properties();
            FileOutputStream fos = new FileOutputStream(path + "/environment.properties");
            props.setProperty("Environment", Platform.getInstance().getPlatformVar());
            props.store(fos, "see https://docs.qameta.io/allure/#_environment");
            fos.close();
        } catch (Exception e) {
            System.err.println("Проблема ввода/вывода с записью файла properties для allure");
            e.printStackTrace();
        }
    }
}
