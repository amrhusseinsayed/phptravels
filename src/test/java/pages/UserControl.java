package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import utils.ActionsUtil;

import java.util.Properties;

public class UserControl extends ActionsUtil {
    public UserControl(WebDriver driver, FluentWait<WebDriver> wait
            , Properties properties) {
        this.driver = driver;
        this.wait = wait;
        this.properties = properties;
    }

    /**
     * This method is used to navigate to the registration form
     * based on the extracted vale of the key that has the url
     * from the config.properties file
     *
     * @return new instance from the registration form page
     */
    public RegistrationPage navigateToRegistrationPage() {
        navigateTo(properties.getProperty("registrationUrl"));
        return new RegistrationPage(driver, wait);
    }
}
