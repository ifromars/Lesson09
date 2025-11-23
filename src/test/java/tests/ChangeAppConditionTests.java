package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    public void testChangeOrientationOnSearchResults(){
        if(Platform.getInstance().isMW()){
            return;
        }
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("язык программирования");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);;
        if (!Platform.getInstance().isiOS()) {
            ArticlePageObject.closeWikipediaGamesSuggestion();
        }
        String titleBeforeRotation = ArticlePageObject.getArticleTitle("Java");
        this.rotateScreenLandscape();

        String titleAfterRotation = ArticlePageObject.getArticleTitle("Java");

        Assertions.assertEquals(titleBeforeRotation, titleAfterRotation,
                "Article title have been changed after rotation");
        this.rotateScreenPortrait();
        String titleAfterSecondRotation = ArticlePageObject.getArticleTitle("Java");
        Assertions.assertEquals(titleBeforeRotation, titleAfterSecondRotation,
                "Article title have been changed after rotation");

    }

    @Test
    public void testCheckSearchInBackground(){

        if(Platform.getInstance().isMW()){
            return;
        }

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("язык программирования");

        this.sendAppToBack(2);
        SearchPageObject.waitForSearchResult("язык программирования");
    }
}
