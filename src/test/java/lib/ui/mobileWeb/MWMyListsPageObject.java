package lib.ui.mobileWeb;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListsPageObject extends MyListsPageObject {

    public MWMyListsPageObject(RemoteWebDriver driver){
        super(driver);
    }
    static{
        ARTICLE_TITLE_TPL = "xpath://li[contains(@class, 'page-summary with-watchstar')]//h3[contains(text(), '{ARTICLE}')]";
        REMOVE_FROM_SAVED_BUTTON = "xpath://li[contains(@class, 'page-summary with-watchstar')]//h3[contains(text(), '{ARTICLE}')]/../../a[contains(@class, 'watched')]";


    }
}
