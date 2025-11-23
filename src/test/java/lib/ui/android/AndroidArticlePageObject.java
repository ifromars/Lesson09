package lib.ui.android;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidArticlePageObject extends ArticlePageObject {

    public AndroidArticlePageObject (RemoteWebDriver driver){
        super(driver);
    }

    static {
            SKIP_GAMES_BUTTON = "androidUIAutomator:new UiSelector().resourceId(\"org.wikipedia:id/closeButton\")";
            TITLE_ELEMENT_TPL = "androidUIAutomator:new UiSelector().text(\"{TITLE}\").instance(0)";
            TITLE = "xpath://android.view.ViewGroup[@resource-id=\"org.wikipedia:id/page_contents_container\"]//android.view.View[@resource-id=\"pcs\"]//android.widget.TextView";
            FOOTER_ELEMENT = "xpath://*[@text = 'Просмотреть статью в браузере']";
            SAVE_PAGE_TO_LIST = "id:org.wikipedia:id/page_save";
            SAVE_PAGE_OK_BUTTON = "id:org.wikipedia:id/snackbar_action";
            SAVE_PAGE_FOLDER_NAME = "id:org.wikipedia:id/text_input";
            OK_BUTTON = "id:android:id/button1";
            FOLDER_ANDROID = "xpath://android.widget.TextView[@resource-id=\"org.wikipedia:id/item_title\" and @text=\"{FOLDER}\"]";
    }
}
