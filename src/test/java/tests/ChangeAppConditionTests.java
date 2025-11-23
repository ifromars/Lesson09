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

@Epic("Тесты изменения условий приложения")
public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature("Поиск"), @Feature("Статья"), @Feature("Ориентация")})
    @DisplayName("Изменение ориентации экрана при просмотре результатов поиска")
    @Description("Проверка что заголовок статьи не изменяется при смене ориентации экрана")
    @Severity(SeverityLevel.NORMAL)
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
    @Features(value = {@Feature("Поиск"), @Feature("Фоновый режим")})
    @DisplayName("Проверка поиска после возврата из фонового режима")
    @Description("Проверка что результаты поиска сохраняются после сворачивания приложения в фон")
    @Severity(SeverityLevel.NORMAL)
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
