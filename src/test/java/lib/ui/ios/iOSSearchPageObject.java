package lib.ui.ios;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSSearchPageObject extends SearchPageObject {

    public iOSSearchPageObject (RemoteWebDriver driver){
        super(driver);
    }

    static {
        SEARCH_INIT_ELEMENT = "accessibilityId:Поиск по Википедии";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@name='Поиск по Википедии']";
        CANCEL_SEARCH_BUTTON = "accessibilityId:Очистить текст";
        SEARCH_RESULT_TPL = "xpath://XCUIElementTypeStaticText[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_ITEM = "xpath://XCUIElementTypeCell//XCUIElementTypeStaticText";
        EMPTY_RESULT_LABEL = "accessibilityId:Ничего не найдено";
    }
}
