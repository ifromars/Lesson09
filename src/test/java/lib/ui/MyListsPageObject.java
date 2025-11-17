package lib.ui;


import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            FOLDER_NAME_TPL,
            POP_UP_MESSAGE_BUTTON,
            ARTICLE_TITLE_TPL,
            IOS_SWIPE_ACTION_DELETE,
            REMOVE_FROM_SAVED_BUTTON;

    public void deleteArticleFromList(){
        this.waitForElementAndClick(
                IOS_SWIPE_ACTION_DELETE,
                "Не найден элемент Удалить из списка",
                90);
    }

    private static String getArticleXpathName(String articleTitle) {
        return ARTICLE_TITLE_TPL.replace("{ARTICLE}", articleTitle);
    }

    private static String getFolderXpathName(String nameOfFolder) {
        return FOLDER_NAME_TPL.replace("{FOLDER}", nameOfFolder);
    }
    private static String getRemoveButtonByTitle(String articleTitle){
        return REMOVE_FROM_SAVED_BUTTON.replace("{ARTICLE}", articleTitle);
    }

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openFolderByName(String nameOfFolder) {
        String nameOfFolderXpath = getFolderXpathName(nameOfFolder);
        this.waitForElementAndClick(nameOfFolderXpath,
                "Список 'Test' не найден",
                90
        );
    }

    public void closePopUpMessage() {
        this.waitForElementAndClick(POP_UP_MESSAGE_BUTTON,
                "ясно, понятно",
                90);
    }

    public void swipeArticleByTitleToDelete(String articleTitle) {
        this.waitForArticleToAppearByTitle(articleTitle);
        String ArticleXpathName = getArticleXpathName(articleTitle);

        if(Platform.getInstance().isMW()){
            String removeLocator = getRemoveButtonByTitle(articleTitle);
            this.waitForElementAndClick(removeLocator, "Cannot remove article",10);
        }else{
        this.swipeElementToLeft(ArticleXpathName,
                "Не найден список"
        );
        }
        if(Platform.getInstance().isMW()){
            driver.navigate().refresh();
        }

        this.waitForArticleToDisappearByTitle(articleTitle);

    }
    public void waitForArticleToDisappearByTitle(String articleTitle){
        String ArticleXpathName = getArticleXpathName(articleTitle);
        this.waitForElementNotPresent(ArticleXpathName,
                "Элемент на странице" + articleTitle,
                90);

    }

    public void waitForArticleToAppearByTitle(String articleTitle){
        String ArticleXpathName = getArticleXpathName(articleTitle);
        this.waitForElementPresent(ArticleXpathName,
                "Элемент на странице" + articleTitle,
                10);

    }

    public void clickForArticleByTitle(String articleTitle){
        String ArticleXpathName = getArticleXpathName(articleTitle);
        this.waitForElementAndClick(ArticleXpathName,
                "Элемент на странице" + articleTitle,
                90);

    }
}
