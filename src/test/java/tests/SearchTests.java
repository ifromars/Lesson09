package tests;

import io.qameta.allure.*;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Тесты для поиска")
public class SearchTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature("Поиск")})
    @DisplayName("Поиск заголовка поиска")
    @Description("Проверка инициализации поля поиска")
    @Severity(SeverityLevel.MINOR)
    public void testFindSearchTitle(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
    }

    @Test
    @Features(value = {@Feature("Поиск")})
    @DisplayName("Поиск статьи")
    @Description("Выполнение поиска по запросу 'Java' и проверка результатов")
    @Severity(SeverityLevel.CRITICAL)
    public void testSearch(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("зык программирования");

    }
    @Test
    @Features(value = {@Feature("Поиск")})
    @DisplayName("Отмена поиска")
    @Description("Проверка функциональности отмены поиска")
    @Severity(SeverityLevel.NORMAL)
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
    @Features(value = {@Feature("Поиск")})
    @DisplayName("Проверка количества результатов поиска")
    @Description("Проверка что поиск возвращает непустые результаты")
    @Severity(SeverityLevel.NORMAL)
    public void testAmountOfNotEmptySearch(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchString = "Дискография Linkin Park";
        SearchPageObject.typeSearchLine(searchString);
        int amountOfSearchResult = SearchPageObject.getAmountOfFoundArticles();

        Assertions.assertTrue(amountOfSearchResult > 0, "Слишком мало результатов");
    }

    @Test
    @Features(value = {@Feature("Поиск")})
    @DisplayName("Проверка пустого результата поиска")
    @Description("Проверка что поиск по несуществующему запросу возвращает пустой результат")
    @Severity(SeverityLevel.NORMAL)
    public void testAmountOfEmptySearch(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchString = "zxcvbasdfqwer";
        SearchPageObject.typeSearchLine(searchString);
        SearchPageObject.waitForEmptySearchResultLabel();
        SearchPageObject.assertThereIsNoResultsOfSearch();
    }
}
