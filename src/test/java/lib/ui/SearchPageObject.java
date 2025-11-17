package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject{

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            CANCEL_SEARCH_BUTTON,
            SEARCH_RESULT_TPL,
            SEARCH_RESULT_ITEM,
            FIND_BUTTON,
            EMPTY_RESULT_LABEL;

    /*Шаблоны*/
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_TPL.replace("{SUBSTRING}", substring);
    }
    /*Шаблоны*/

    public SearchPageObject(RemoteWebDriver driver){
        super(driver);
    }

    public void initSearchInput(){

        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "не могу поле поиска после нажатия на поиск");
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "не могу найти поиск", 90);
    }
    public void typeSearchLine(String searchLine){
        this.waitForElementAndSendKeys(SEARCH_INPUT, searchLine,"Не могу написать в поиск",90);
        if(Platform.getInstance().isMW()){
            waitForElementAndClick(FIND_BUTTON,"нет кнопки Найти", 3);
        }
    }

    public void waitForCancelSearchButtonToAppear(){
        this.waitForElementPresent(CANCEL_SEARCH_BUTTON, "Кнопки отменить поиск нет");


    }
    public void waitForCancelSearchButtonToDisappear(){
        this.waitForElementNotPresent(CANCEL_SEARCH_BUTTON,"Кнопка еще тут",90);
    }
    public void clickCancelSearchButton(){
        this.waitForElementAndClick(CANCEL_SEARCH_BUTTON,"Кнопка не нажалась",90);
    }
    public void waitForSearchResult(String substring){
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(searchResultXpath, "Текст не найден по xpath " + searchResultXpath);
    }
    public void clickByArticleWithSubstring(String substring){
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(searchResultXpath, "Текст не найден по xpath, клик невозможен " + searchResultXpath,  90);
    }
    public int getAmountOfFoundArticles(){

        this.waitForElementPresent(SEARCH_RESULT_ITEM,
                "Не могу найти текст на странице ",
                90
        );
        return this.getAmountOfElements(SEARCH_RESULT_ITEM);
    }
    public void waitForEmptySearchResultLabel(){

        this.waitForElementPresent(EMPTY_RESULT_LABEL,
                "Не могу найти надпись по запросу",
                90
        );
    }

    public void assertThereIsNoResultsOfSearch(){
        this.assertElementNotPresent(
                SEARCH_RESULT_ITEM,
                "Поиск не пустой."
        );
    }
}
