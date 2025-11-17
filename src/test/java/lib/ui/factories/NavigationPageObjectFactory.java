package lib.ui.factories;

import lib.Platform;
import lib.ui.NavigationUI;
import lib.ui.android.AndroidNavigationUI;
import lib.ui.ios.iOSNavigationUI;
import lib.ui.mobileWeb.MWNavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationPageObjectFactory {

    public static NavigationUI get(RemoteWebDriver driver){
        if(Platform.getInstance().isAndroid()) {
            return new AndroidNavigationUI(driver);
        }else if (Platform.getInstance().isiOS()){
            return new iOSNavigationUI(driver);
        }else{
            return new MWNavigationUI(driver);
        }
    }
}

