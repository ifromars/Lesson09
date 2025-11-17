package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSArticlePageObject extends ArticlePageObject {

    public iOSArticlePageObject (RemoteWebDriver driver){
        super(driver);
    }


    static {
        SKIP_GAMES_BUTTON = "accessibilityId:Close";
        TITLE_ELEMENT_TPL = "xpath://XCUIElementTypeNavigationBar[contains(@name,'{TITLE}')]";
        TITLE = "id:Java";
        FOOTER_ELEMENT = "xpath://XCUIElementTypeStaticText[@name=\"Просмотреть статью в браузере\"]";
        SAVE_PAGE_TO_LIST = "accessibilityId:Сохранить на потом";
        SAVE_PAGE_OK_BUTTON = "accessibilityId:add-to-list";
        SAVE_PAGE_FOLDER_NAME = "iOSClassChain:**/XCUIElementTypeTextField[`value == \"заголовок списка для чтения\"`]";
        OK_BUTTON = "iOSClassChain:**/XCUIElementTypeStaticText[`name == \"Создать список для чтения\"`]";
        CREATE_NEW_LIST = "iOSClassChain:**/XCUIElementTypeStaticText[`name == \"Создать новый список\"`]";
        GOT_IT = "accessibilityId:Понятно";

    }
}
