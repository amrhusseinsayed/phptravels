package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import utils.ActionsUtil;

public class HomePage extends ActionsUtil {
    private static Logger log = Logger.getLogger(HomePage.class);

    @CacheLookup
    @FindBy(css = "[href='#profile']")
    private WebElement myProfile;

    public HomePage(WebDriver driver, FluentWait<WebDriver> wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    /**
     * This method is used to assert whether the registration is completed
     * or not by waiting for the My Profile menu in the Homepage to
     * be visible
     */
    public void waitForUserRegistration() {
        try {
            log.debug("Waiting for the My Profile menu to be visible");
            waitForElementVisibility(myProfile);
            logInfo("My Profile menu is now visible");
        } catch (Exception e) {
            logError("Exception while waiting for the My Profile menu to be visible");
            throw e;
        }
    }
}
