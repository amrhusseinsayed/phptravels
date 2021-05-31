package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Level;

public class ActionsUtil {
    private static Logger log = Logger.getLogger(ActionsUtil.class);
    protected Properties properties;
    protected WebDriver driver;
    protected FluentWait<WebDriver> wait;
    protected JSONObject testData = new JSONObject();
    protected static ExtentTest test;
    protected static ExtentReports report;

    /**
     * This method is used to initialize the browser instance and the fluent wait to be used
     * during the execution
     *
     * @throws Exception in case of not being able to initialize the browser
     *                   instance and the fluent wait
     */
    protected void initializeBrowser() throws Exception {
        logInfo("Initializing the Browser instance");

        var timeout = Long.parseLong(properties.getProperty("timeout"));
        var polling = Long.parseLong(properties.getProperty("polling"));
        var browser = properties.getProperty("browser");
        var runHeadless = Boolean.parseBoolean(properties.getProperty("runHeadless"));
        var pageLoadStrategy = getPageLoadStrategy(properties.getProperty("pageLoadStrategy"));
        var driversPath = System.getProperty("user.dir")
                + properties.getProperty("driversPath");

        switch (browser) {
            case "chrome":
                LoggingPreferences logPrefs = new LoggingPreferences();
                logPrefs.enable(LogType.BROWSER, Level.SEVERE);

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("test-type");
                chromeOptions.addArguments("--js-flags=--expose-gc");
                chromeOptions.addArguments("--enable-precise-memory-info");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-default-apps");
                chromeOptions.addArguments("test-type=browser");
                chromeOptions.addArguments("disable-infobars");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-device-discovery-notifications");
                chromeOptions.setExperimentalOption("excludeSwitches"
                        , Collections.singletonList("enable-automation"));
                chromeOptions.setHeadless(runHeadless);
                chromeOptions.setPageLoadStrategy(pageLoadStrategy);
                chromeOptions.setCapability("perfLoggingPrefs", logPrefs);
                chromeOptions.setAcceptInsecureCerts(true);
                System.setProperty("webdriver.chrome.driver", driversPath + "/chromedriver.exe");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                ProfilesIni profilesIni = new ProfilesIni();
                FirefoxProfile firefoxProfile = profilesIni.getProfile("default");

                FirefoxOptions ffOptions = new FirefoxOptions();
                ffOptions.addPreference("dom.webnotifications.enabled", false);
                ffOptions.setProfile(firefoxProfile);
                ffOptions.setLogLevel(FirefoxDriverLogLevel.ERROR);
                ffOptions.setHeadless(runHeadless);
                ffOptions.setPageLoadStrategy(pageLoadStrategy);
                System.setProperty("webdriver.gecko.driver", driversPath + "/geckodriver.exe");
                driver = new FirefoxDriver(ffOptions);
                break;
            case "ie":
                InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                ieOptions.setPageLoadStrategy(pageLoadStrategy);
                System.setProperty("webdriver.ie.driver.loglevel", "ERROR");
                System.setProperty("webdriver.ie.driver", driversPath + "/IEdriverServer.exe");
                driver = new InternetExplorerDriver(ieOptions);
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setPageLoadStrategy(pageLoadStrategy);
                System.setProperty("webdriver.edge.driver", driversPath + "/MicrosoftWebDriver.exe");
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                throw new Exception(String.format("Undefined browser type: '%s'", browser));
        }

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        wait = new FluentWait<>(driver).withTimeout(Duration.ofMinutes(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class).ignoring(UnknownError.class);
    }

    /**
     * This method is used to get the Page Load Strategy base on the given strategy as a string
     *
     * @param pageLoadStrategy the page load strategy as a string
     * @return the Page Load Strategy
     * @throws Exception in case of the given strategy string is invalid
     */
    protected PageLoadStrategy getPageLoadStrategy(String pageLoadStrategy) throws Exception {
        switch (pageLoadStrategy.toLowerCase()) {
            case "normal":
                return PageLoadStrategy.NORMAL;
            case "eager":
                return PageLoadStrategy.EAGER;
            case "none":
                return PageLoadStrategy.NONE;
            default:
                throw new Exception(String.format("Invalid Page Load Strategy: '%s'"
                        , pageLoadStrategy));
        }
    }

    /**
     * This method is used to navigate to the given URL
     *
     * @param url the URL to navigate to
     */
    protected void navigateTo(String url) {
        try {
            logInfo(String.format("Navigating to the URL: '%s'", url));
            driver.get(url);
        } catch (Exception e) {
            logError(String.format("Exception while trying to navigate to the URL: '%s'"
                    , url));
            throw e;
        }
    }

    /**
     * This method is used to add the given text to teh given element
     *
     * @param element the element to add the text inside
     * @param text    the text to be added inside the element
     */
    protected void addText(WebElement element, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.sendKeys(text);
    }

    /**
     * This method is used to click on the given element
     *
     * @param element        the element to click on
     * @param userJavascript a flag to indicate the behavior of the click,
     *                       if its true, it will use the Javascript executor to
     *                       perform the action otherwise it will use the normal
     *                       selenium click
     */
    protected void clickOn(WebElement element, boolean userJavascript) {
        wait.until(ExpectedConditions.elementToBeClickable(element));

        if (userJavascript) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                    element);
        } else {
            element.click();
        }
    }

    /**
     * This method is used to wait for the given element
     * to be visible
     *
     * @param element the element to wait for its visibility
     */
    protected void waitForElementVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * This method is used to wait for the given text to be visible
     * within the current html pae
     *
     * @param text the text to wait for
     */
    protected void waitForTextToBeVisible(String text) {
        wait.until(d -> {
            try {
                WebElement body = driver.findElement(By.tagName("body"));
                return body.getText().contains(text);
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * This method is used to wait for the given value of the given attribute related
     * to the given WebElement to be visible
     *
     * @param element   the element that has the attribute to wait for its value
     * @param attribute the attribute of the given element to wait for its value
     * @param value     the value of the given attribute to wait for
     */
    protected void waitForElementAttributeValueToBe(WebElement element
            , String attribute, String value) {
        wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    /**
     * This method is used to completely close the current browser instance
     */
    protected void closeDriver() {
        try {
            logInfo("Closing the running browser instance");
            driver.close();
            driver.quit();
        } catch (Exception e) {
            logError("Exception while trying to close the running browser instance");
            throw e;
        }
    }

    /**
     * This method is used to log an INFO log inside both the log
     * txt file and the html report
     *
     * @param message the message to be logged
     */
    protected void logInfo(String message) {
        log.info(message);
        test.log(LogStatus.INFO, message);
    }

    /**
     * This method is used to log an ERROR log inside both the log
     * txt file and the html report
     *
     * @param message the message to be logged
     */
    protected void logError(String message) {
        log.error(message);
        test.log(LogStatus.ERROR, message);
    }

    /**
     * This method is used to log an INFO log inside the log txt file
     * and a PASS log inside the html report
     *
     * @param message the message to be logged
     */
    protected void logSuccess(String message) {
        log.info(message);
        test.log(LogStatus.PASS, message);
    }

    /**
     * This method is used to log an ERROR log inside the log txt file
     * and a FAIL log inside the html report
     *
     * @param message the message to be logged
     */
    protected void logFail(String message) {
        log.error(message);
        test.log(LogStatus.FAIL, message);
    }

    /**
     * This method is used to log an INFO log inside the log txt file
     * and a SKIP log inside the html report
     *
     * @param message the message to be logged
     */
    protected void logSkip(String message) {
        log.info(message);
        test.log(LogStatus.SKIP, message);
    }

    /**
     * This method is used to log an INFO log inside the log txt file
     * and an UNKNOWN log inside the html report
     *
     * @param message the message to be logged
     */
    protected void logUnknown(String message) {
        log.info(message);
        test.log(LogStatus.UNKNOWN, message);
    }
}
