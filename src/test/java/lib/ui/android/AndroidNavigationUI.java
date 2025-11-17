package lib.ui.android;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidNavigationUI extends NavigationUI {

    static {
        SAVED_BUTTON = "androidUIAutomator:new UiSelector().resourceId(\"org.wikipedia:id/navigation_bar_item_icon_view\").instance(1)";
        GO_BACK_BUTTON = "accessibilityId:Перейти вверх";
    }

    public AndroidNavigationUI (RemoteWebDriver driver){
        super(driver);
    }
}
