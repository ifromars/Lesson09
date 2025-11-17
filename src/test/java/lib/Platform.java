package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {

    private static Platform instance;
    private Platform(){}

    public static Platform getInstance(){
        if(instance == null){
            instance = new Platform();
        }
        return instance;
    }

    private static final String PLATFORM_IOS="iOS";
    private static final String PLATFORM_ANDROID="android";
    private static final String PLATFORM_MOBILE_WEB = "mobile_web";
    private static final String APPIUM_URL = "http://localhost:4723/";
    private static final String APP_PATH = new File("apks/org.wikipedia.apk").getAbsolutePath();
    private static final String APP_PATH_iOS = new File("apks/Wikipedia.app").getAbsolutePath();

    public boolean isAndroid(){
        return isPlatform(PLATFORM_ANDROID);
    }
    public boolean isiOS(){
        return isPlatform(PLATFORM_IOS);
    }
    public boolean isMW(){
        return isPlatform(PLATFORM_MOBILE_WEB);
    }

    public RemoteWebDriver getDriver() throws Exception {

        URL URL = new URI(APPIUM_URL).toURL();

        if(this.isAndroid()){
            return new AndroidDriver(URL, this.getAndroidCapabilities());
       } else if (this.isiOS()) {
            return new IOSDriver(URL, this.getiOSCapabilities());
        }else if (this.isMW()) {
            return new ChromeDriver(this.getMWChromeOptions());
        }else {
            throw new Exception ("Cannot define platform. platform equals = " + this.getPlatformVar());
        }


    }

    private Capabilities getAndroidCapabilities(){
        Capabilities capabilities = new BaseOptions()
                .amend("platformName", "Android")
                .amend("appium:deviceName", "emulator-5554")
                .amend("appium:appPackage", "org.wikipedia")
                .amend("appium:appActivity", ".main.MainActivity")
                .amend("appium:automationName", "UiAutomator2")
                .amend("appium:app", APP_PATH);
        return capabilities;
    }

    private Capabilities getiOSCapabilities(){
        Capabilities capabilities = new BaseOptions()
                .amend("platformName", "iOS")
                .amend("appium:platformVersion", "18.1")
                .amend("appium:deviceName", "iPhone 16")
                .amend("appium:automationName", "XCUITest")
                .amend("appium:app", APP_PATH_iOS)
                .amend("wdaLaunchTimeout", 1000000);
        return capabilities;
    }

    private ChromeOptions getMWChromeOptions(){
        Map<String, Object> deviceMetrics = new HashMap<String, Object>();
        deviceMetrics.put("width", 480);
        deviceMetrics.put("height", 1080);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<String, Object>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 13; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Mobile Safari/537.36");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        chromeOptions.addArguments("window-size=480,1080");
        return chromeOptions;
    }

    public String getPlatformVar(){
        return System.getenv("PLATFORM");
    }

    private boolean isPlatform(String myPlatform){
        String platform = this.getPlatformVar();
        return myPlatform.equals(platform);
    }
}
