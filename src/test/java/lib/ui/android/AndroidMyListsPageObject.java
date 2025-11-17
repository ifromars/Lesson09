package lib.ui.android;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidMyListsPageObject extends MyListsPageObject {

    static{
        FOLDER_NAME_TPL = "androidUIAutomator:new UiSelector().text(\"{FOLDER}\")";
        POP_UP_MESSAGE_BUTTON = "androidUIAutomator:new UiSelector().text(\"Понятно\")";
        ARTICLE_TITLE_TPL = "androidUIAutomator:new UiSelector().text(\"{ARTICLE}\")";
        IOS_SWIPE_ACTION_DELETE = "accessibilityId:swipe action delete";
    }

    public AndroidMyListsPageObject (RemoteWebDriver driver){
        super(driver);
    }
}
