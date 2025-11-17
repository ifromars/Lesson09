package lib.ui.android;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidSearchPageObject extends SearchPageObject {

    public AndroidSearchPageObject (RemoteWebDriver driver){
        super (driver);
    }

    static {
            SEARCH_INIT_ELEMENT = "xpath://*[@text='Поиск по Википедии']";
            SEARCH_INPUT = "xpath://*[@text = 'Поиск по Википедии']";
            CANCEL_SEARCH_BUTTON = "id:org.wikipedia:id/search_close_btn";
            SEARCH_RESULT_TPL = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[contains(@text,'{SUBSTRING}')]";
            SEARCH_RESULT_ITEM = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']";
            EMPTY_RESULT_LABEL="xpath://*[@text='Ничего не найдено']";
    }
}
