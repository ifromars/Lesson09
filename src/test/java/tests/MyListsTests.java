package tests;

import lib.CoreTestCase;
import lib.EnvConfig;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationPageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private String nameOfFolder = "Test";
    //Сделал логин и пароль через .env потому что репозиторий публичный
    private static final String
            login = EnvConfig.getWikipediaLogin(),
            password = EnvConfig.getWikipediaPassword();

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Java");

        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            articlePage.closeWikipediaGamesSuggestion();
        }
        articlePage.waitForTitleElement("Java");
        if (Platform.getInstance().isiOS()){
            articlePage.clickGotItiOS();
            articlePage.clickGotItiOS();
            articlePage.clickGotItiOS();
        }

        String articleTitle = articlePage.getArticleTitle();
        if (Platform.getInstance().isAndroid()) {
            articlePage.saveArticleToMyList(nameOfFolder);
        } else {
            articlePage.saveArticleToSaved();
        }
        if(Platform.getInstance().isMW()){
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
            authorizationPageObject.enterLoginData(login, password);
            authorizationPageObject.submitForm();

            articlePage.waitForTitleElement(articleTitle);

            assertEquals("We are not on the same page",
                    articleTitle,
                    articlePage.getArticleTitle()
            );
            articlePage.saveArticleToSaved();
        }

        NavigationUI navigationUI = NavigationPageObjectFactory.get(driver);

        navigationUI.openNavigation();
        navigationUI.goBackButtonClick();

        if (Platform.getInstance().isAndroid()) {
            navigationUI.goBackButtonClick();
        }
        navigationUI.clickSaved();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(nameOfFolder);
        }
        if (!Platform.getInstance().isMW()){
            myListsPageObject.closePopUpMessage();
        }
        myListsPageObject.swipeArticleByTitleToDelete(articleTitle);
        if (Platform.getInstance().isiOS()){
            myListsPageObject.deleteArticleFromList();
        }
    }

    @Test
    public void testSaveTwoArticlesToMyListAndDeleteOneOfTheArticles() {
        String nameOfFolder = "Всякое разное";

        // === Сохраняем первую статью: Java ===
        SearchPageObject searchPage = SearchPageObjectFactory.get(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine("Java");
        if(!Platform.getInstance().isMW()) {
            searchPage.clickByArticleWithSubstring("зык программирования");
        }else{
            searchPage.clickByArticleWithSubstring("Java");
        }

        ArticlePageObject articlePage = ArticlePageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            articlePage.closeWikipediaGamesSuggestion();
        }
        articlePage.waitForTitleElement("Java");
        if (Platform.getInstance().isiOS()) {
            articlePage.clickGotItiOS();
            articlePage.clickGotItiOS();
            articlePage.clickGotItiOS();
        }

        String javaTitle = articlePage.getArticleTitle("Java");

        if (Platform.getInstance().isAndroid()) {
            articlePage.saveArticleToMyList(nameOfFolder);
        } else {
            articlePage.saveArticleToSaved();
        }
        if(Platform.getInstance().isMW()){
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
            authorizationPageObject.enterLoginData(login, password);
            authorizationPageObject.submitForm();

            articlePage.waitForTitleElement(javaTitle);

            assertEquals("We are not on the same page",
                    javaTitle,
                    articlePage.getArticleTitle()
            );
            articlePage.saveArticleToSaved();
        }

        NavigationUI navigationUI = NavigationPageObjectFactory.get(driver);


        navigationUI.goBackButtonClick();

        if (Platform.getInstance().isAndroid()) {
            navigationUI.goBackButtonClick(); // Возврат к результатам поиска на Android
        }else if (Platform.getInstance().isiOS()){
            searchPage.clickCancelSearchButton();
        }else{
            navigationUI.navigateToMainPage();
        }

        // === Сохраняем вторую статью: APK ===
        searchPage.initSearchInput();
        searchPage.typeSearchLine("APK");
        searchPage.clickByArticleWithSubstring("APK");

        articlePage.waitForTitleElement("APK");

        String apkTitle = articlePage.getArticleTitle("APK");

        if (Platform.getInstance().isAndroid()) {
            articlePage.saveArticleToExistingList(nameOfFolder);
        } else {
            articlePage.saveArticleToSaved();
        }

        // Возврат к главному экрану
        navigationUI.goBackButtonClick();
        if (Platform.getInstance().isAndroid()) {
            navigationUI.goBackButtonClick();
        }
        navigationUI.openNavigation();
        // Переход в "Сохранённые"
        navigationUI.clickSaved();

        // Открытие папки (только на Android)
        MyListsPageObject myListsPage = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPage.openFolderByName(nameOfFolder);
        }
        if (!Platform.getInstance().isMW()){
            myListsPage.closePopUpMessage();
        }
        // Удаление статьи "Java"
        myListsPage.swipeArticleByTitleToDelete(javaTitle);
        if (Platform.getInstance().isiOS()) {
            myListsPage.deleteArticleFromList();
        }

        // Проверка: Java удалена, APK остаётся
        myListsPage.waitForArticleToDisappearByTitle(javaTitle);
        myListsPage.waitForArticleToAppearByTitle(apkTitle);

        // Дополнительная проверка: заголовок APK не изменился
        myListsPage.clickForArticleByTitle(apkTitle);
        articlePage.waitForTitleElement(apkTitle);
        String titleAfterDeletion = articlePage.getArticleTitle(apkTitle);

        Assert.assertEquals(
                "Название статьи изменилось после удаления другой статьи",
                apkTitle,
                titleAfterDeletion
        );
    }
}
