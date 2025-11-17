package lib.ui.mobileWeb;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    public MWSearchPageObject (RemoteWebDriver driver){
        super(driver);
    }
    static {
        SEARCH_INIT_ELEMENT = "css:span[class~=\"main-top-mobileSearchButton\"]>a[title=\"Служебная:Поиск\"]";
        SEARCH_INPUT = "css:div[id=\"searchText\"]>input[type = \"search\"]";
        CANCEL_SEARCH_BUTTON = "css:span.oo-ui-indicator-clear";
        SEARCH_RESULT_TPL = "xpath://div[@class=\"mw-search-result-heading\"]//*[contains(text(), '{SUBSTRING}')]";
        SEARCH_RESULT_ITEM = "css:ul.mw-search-results>li.mw-search-result";
        EMPTY_RESULT_LABEL="css:p.mw-search-nonefound";
        FIND_BUTTON = "css:button[type=\"submit\"]";
    }
}
