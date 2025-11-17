package lib.ui.ios;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSNavigationUI extends NavigationUI {

    static {
        SAVED_BUTTON = "accessibilityId:tabbar-save";
        GO_BACK_BUTTON = "accessibilityId:Лента";
    }

    public iOSNavigationUI (RemoteWebDriver driver){
        super(driver);
    }
}
