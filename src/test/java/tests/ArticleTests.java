package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {
    @Test
    public void testCompareArticleTest(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Java");
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.closeWikipediaGamesSuggestion();
        }
        String articleTitle =ArticlePageObject.getArticleTitle("Java");

        assertEquals("Неожиданное сообщение",
                "Java",
                articleTitle);
    }

    @Test
    public void testSwipeArticleTest(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Airbus");
        SearchPageObject.clickByArticleWithSubstring("Airbus");
        // Пропускаем шаг на iOS
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.closeWikipediaGamesSuggestion();
        }
        if (Platform.getInstance().isiOS()){
            ArticlePageObject.clickGotItiOS();
            ArticlePageObject.clickGotItiOS();
            ArticlePageObject.clickGotItiOS();
        }

        ArticlePageObject.waitForTitleElement("Airbus");
        ArticlePageObject.swipeToFooter();

    }
}
