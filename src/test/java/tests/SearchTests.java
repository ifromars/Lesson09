package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testFindSearchTitle(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
    }

    @Test
    public void testSearch(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("зык программирования");

    }
    @Test
    public void testCancelSearch(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("язык программирования");
        SearchPageObject.waitForCancelSearchButtonToAppear();
        SearchPageObject.clickCancelSearchButton();
        SearchPageObject.waitForCancelSearchButtonToDisappear();
    }




    @Test
    public void testAmountOfNotEmptySearch(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchString = "Дискография Linkin Park";
        SearchPageObject.typeSearchLine(searchString);
        int amountOfSearchResult = SearchPageObject.getAmountOfFoundArticles();

        assertTrue("Слишком мало результатов",
                amountOfSearchResult > 0);
    }

    @Test
    public void testAmountOfEmptySearch(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchString = "zxcvbasdfqwer";
        SearchPageObject.typeSearchLine(searchString);
        SearchPageObject.waitForEmptySearchResultLabel();
        SearchPageObject.assertThereIsNoResultsOfSearch();
    }
}
