package lib.ui.mobileWeb;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    public MWArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }

    static {

        TITLE_ELEMENT_TPL = "xpath://h1[@id='firstHeading']/span[contains(text(), '{TITLE}')]";
        FOOTER_ELEMENT = "css:footer";
        SAVE_PAGE_TO_LIST = "css:#ca-watch > span.minerva-icon.minerva-icon--star";
        OPTIONS_REMOVE_FROM_MY_LIST = "xpath://nav[contains(@class, 'page-actions-menu')]//a[contains(@href, '=unwatch') and @role='button']";
        TITLE = "css:span.mw-page-title-main";//css:#ca-watch > span.minerva-icon.minerva-icon--star
    }
}
