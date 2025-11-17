package lib.ui;


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

    public void closeWikipediaGamesSuggestion(){
        this.waitForElementAndClick(SKIP_GAMES_BUTTON, "Кнопки 'Пропустить' нет", 15);
    }

    public void clickGotItiOS(){
        this.waitForElementAndClick(GOT_IT, "Кнопки 'Понятно' нет", 90);
    }

    public WebElement waitForTitleElement(String substring){
        String getTitleElement = getTitleElement(substring);
        return  this.waitForElementPresent(getTitleElement,
                "Не могу найти заголовок",
                90);
    }

    public WebElement waitForTitleElementSimple(){

        return  this.waitForElementPresent(TITLE,
                "Не могу найти заголовок",
                90);
    }

    public String getArticleTitle(String substring){
        WebElement title_element = waitForTitleElement(substring);
        if(Platform.getInstance().isAndroid()){
            return title_element.getAttribute("text");
        }else if(Platform.getInstance().isiOS()){
            return title_element.getAttribute("name");
        }else{
            return title_element.getText();
        }
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElementSimple();
        if(Platform.getInstance().isAndroid()){
            return title_element.getAttribute("text");
        }else if(Platform.getInstance().isiOS()){
            return title_element.getAttribute("name");
        }else{
            return title_element.getText();
        }
    }

    public void swipeToFooter(){

        if(Platform.getInstance().isiOS()){
            this.swipeUpTillElementAppears(FOOTER_ELEMENT,"Футер статьи не найден",40);
        }else if(Platform.getInstance().isiOS()){
        this.swipeUpToFindElement(FOOTER_ELEMENT,
                "Футер статьи не найден",
                90);
        }else{
            this.scrollWebPageTillElementNotVisible(FOOTER_ELEMENT, "Footer of webPage not found", 40);
        }
    }

    public void saveArticleToMyList(String nameOfFolder){
        this.waitForElementAndClick(SAVE_PAGE_TO_LIST,
                "Элемент 'Сохранить в список' не найден",
                90
        );
        this.waitForElementAndClick(SAVE_PAGE_OK_BUTTON,
                "Тост не найден",
                90
        );
        this.waitForElementAndSendKeys(SAVE_PAGE_FOLDER_NAME,
                nameOfFolder,
                "Поле ввода не найдено" ,
                90);
        this.waitForElementAndClick(OK_BUTTON,
                "Элемент 'Ок' не найден",
                90
        );
    }

    public void saveArticleToSaved(){
        if(Platform.getInstance().isMW()){
            this.removeArticleFromSavedIfItWasAdded();
        }
        this.waitForElementAndClick(SAVE_PAGE_TO_LIST,
                "Элемент 'Сохранить в список' не найден",
                90
        );
    }

    public void saveArticleToExistingList(String nameOfFolder) {
        String folderName = getFolderName(nameOfFolder);
        this.waitForElementAndClick(SAVE_PAGE_TO_LIST,
                "Элемент 'Сохранить в список' не найден",
                90
        );
        this.waitForElementAndClick(SAVE_PAGE_OK_BUTTON,
                "Тост не найден",
                90
        );
        waitForElementAndClick(folderName,
                "Список не найден",
                10
        );
    }

    public void removeArticleFromSavedIfItWasAdded(){
        if(this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST)){
            this.waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST, "cannot remove from list", 5);
        }
        this.waitForElementPresent(SAVE_PAGE_TO_LIST, "cannot find button save to list",5);
    }
}
