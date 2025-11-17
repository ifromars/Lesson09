package lib.ui.mobileWeb;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {

    public MWNavigationUI(RemoteWebDriver driver){
        super(driver);
    }

    static {
        SAVED_BUTTON = "css:a[data-event-name='menu.watchlist']";
        OPEN_NAVIGATION = "css:#main-menu-input";
    }
}
