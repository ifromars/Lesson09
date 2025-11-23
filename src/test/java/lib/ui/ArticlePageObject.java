package lib.ui;


import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            SKIP_GAMES_BUTTON,
            TITLE_ELEMENT_TPL,
            FOOTER_ELEMENT,
            SAVE_PAGE_TO_LIST,
            SAVE_PAGE_OK_BUTTON,
            SAVE_PAGE_FOLDER_NAME,
            OPTIONS_REMOVE_FROM_MY_LIST,
            OK_BUTTON,
            TITLE,
            CREATE_NEW_LIST,
            GOT_IT,
            FOLDER_ANDROID;

    private static String getTitleElement(String substring){
        return TITLE_ELEMENT_TPL.replace("{TITLE}", substring);
    }
    private static String getFolderName(String substring){
        return FOLDER_ANDROID.replace("{FOLDER}", substring);
    }

    public ArticlePageObject (RemoteWebDriver driver){
        super (driver);
    }
    @Step("Закрытие предложения сыграть в игры на Википедия")
    public void closeWikipediaGamesSuggestion(){
        this.waitForElementAndClick(SKIP_GAMES_BUTTON, "Кнопки 'Пропустить' нет", 15);
    }
    @Step("Нажатие кнопки Понятно на iOS")
    public void clickGotItiOS(){
        this.waitForElementAndClick(GOT_IT, "Кнопки 'Понятно' нет", 10);
    }
    @Step("Ожидание появления заголовка")
    public WebElement waitForTitleElement(String substring){
        String getTitleElement = getTitleElement(substring);
        return  this.waitForElementPresent(getTitleElement,
                "Не могу найти заголовок",
                10);
    }
    @Step("Ожидание появления заголовка упрощенно")
    public WebElement waitForTitleElementSimple(){

        return  this.waitForElementPresent(TITLE,
                "Не могу найти заголовок",
                10);
    }
    @Step("Получить название статьи")
    public String getArticleTitle(String substring){
        WebElement title_element = waitForTitleElement(substring);
        screenshot(this.takeScreenshot("articleTitle"));
        if(Platform.getInstance().isAndroid()){
            return title_element.getAttribute("text");
        }else if(Platform.getInstance().isiOS()){
            return title_element.getAttribute("name");
        }else{
            return title_element.getText();
        }
    }
    @Step("Получить название статьи")
    public String getArticleTitle(){
        WebElement title_element = waitForTitleElementSimple();
        screenshot(this.takeScreenshot("articleTitle"));
        if(Platform.getInstance().isAndroid()){
            return title_element.getAttribute("text");
        }else if(Platform.getInstance().isiOS()){
            return title_element.getAttribute("name");
        }else{
            return title_element.getText();
        }
    }
    @Step("Прокрутка до футера статьи")
    public void swipeToFooter(){

        if(Platform.getInstance().isiOS()){
            this.swipeUpTillElementAppears(FOOTER_ELEMENT,"Футер статьи не найден",40);
        }else if(Platform.getInstance().isAndroid()){
        this.swipeUpToFindElement(FOOTER_ELEMENT,
                "Футер статьи не найден",
                40);
        }else{
            this.scrollWebPageTillElementNotVisible(FOOTER_ELEMENT, "Footer of webPage not found", 40);
        }
    }
    @Step("Сохранение статьи в Мой список для андроид")
    public void saveArticleToMyList(String nameOfFolder){
        this.waitForElementAndClick(SAVE_PAGE_TO_LIST,
                "Элемент 'Сохранить в список' не найден",
                10
        );
        this.waitForElementAndClick(SAVE_PAGE_OK_BUTTON,
                "Тост не найден",
                10
        );
        this.waitForElementAndSendKeys(SAVE_PAGE_FOLDER_NAME,
                nameOfFolder,
                "Поле ввода не найдено" ,
                10);
        this.waitForElementAndClick(OK_BUTTON,
                "Элемент 'Ок' не найден",
                10
        );
    }
    @Step("Сохраниене статьи в список для iOS или мобильной версии браузера")
    public void saveArticleToSaved(){
        if(Platform.getInstance().isMW()){
            this.removeArticleFromSavedIfItWasAdded();
        }
        this.waitForElementAndClick(SAVE_PAGE_TO_LIST,
                "Элемент 'Сохранить в список' не найден",
                10
        );
    }
    @Step("Сохранение статьи в существующий список андроид")
    public void saveArticleToExistingList(String nameOfFolder) {
        String folderName = getFolderName(nameOfFolder);
        this.waitForElementAndClick(SAVE_PAGE_TO_LIST,
                "Элемент 'Сохранить в список' не найден",
                10
        );
        this.waitForElementAndClick(SAVE_PAGE_OK_BUTTON,
                "Тост не найден",
                10
        );
        waitForElementAndClick(folderName,
                "Список не найден",
                10
        );
    }
    @Step("Удалить статью из списка, если она уже была туда добавлена")
    public void removeArticleFromSavedIfItWasAdded(){
        if(this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST)){
            this.waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST, "cannot remove from list", 5);
        }
        this.waitForElementPresent(SAVE_PAGE_TO_LIST, "cannot find button save to list",5);
    }
}
