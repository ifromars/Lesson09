package lib.ui.ios;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSMyListsPageObject extends MyListsPageObject {

    static{

        POP_UP_MESSAGE_BUTTON = "accessibilityId:Закрыть";
        ARTICLE_TITLE_TPL = "xpath://XCUIElementTypeStaticText[contains(@name,'{c}')]";
        IOS_SWIPE_ACTION_DELETE = "accessibilityId:swipe action delete";
    }

    public iOSMyListsPageObject (RemoteWebDriver driver){
        super(driver);
    }
}
