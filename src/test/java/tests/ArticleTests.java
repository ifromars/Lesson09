package tests;

import io.qameta.allure.*;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Тесты для статей")
public class ArticleTests extends CoreTestCase {
    @Test
    @Features(value = {@Feature("Поиск"), @Feature("Статья")})
    @DisplayName("Сравнение заголовка с ожидаемым")
    @Description("Открываем статью Java и проверяем что у ней заголовок который мы ожидаем")
    @Severity(SeverityLevel.BLOCKER)
    public void testCompareArticleTest(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Java");
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.closeWikipediaGamesSuggestion();
        }
        String articleTitle = ArticlePageObject.getArticleTitle();
//        ArticlePageObject.takeScreenshot("articleTitle");
        Assertions.assertEquals("Java", articleTitle, "Неожиданное сообщение");
    }

    @Test
    @Features(value = {@Feature("Поиск"), @Feature("Статья")})
    @DisplayName("Прокрутка статьи до футера")
    @Description("Открываем статью и прокручиваем до футера")
    @Severity(SeverityLevel.MINOR)
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
