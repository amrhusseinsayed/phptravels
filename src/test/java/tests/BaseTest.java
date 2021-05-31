package tests;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.*;

import java.util.Arrays;

public class BaseTest extends ActionsUtil {
    private static Logger log = Logger.getLogger(BaseTest.class);

    /**
     * This method is executed before all the test case to extract
     * the run properties from the config.properties file, extract
     * the test data from the users.json file, initialize the html
     * report before the run and finally to load the configuration
     * of the Log4j through its configuration file path added to
     * the properties file
     *
     * @throws Exception in case of facing an issue while
     *                   initializing any of the mentioned above
     */
    @BeforeSuite
    public void beforeSuite() throws Exception {
        var testDataFilePath = System.getProperty("user.dir")
                + "/src/test/resources/testData/users.json";

        var propertyFilePath = System.getProperty("user.dir")
                + "/src/test/resources/configurations/config.properties";

        properties = PropertiesFileUtil.getProperties(propertyFilePath);
        testData = JsonFileUtil.getJson(testDataFilePath);
        report = ExtentReportUtil.initializeHtmlReport(properties);
        Log4jUtil.loadConfigurations(System.getProperty("user.dir")
                + properties.getProperty("log4jPath"));
    }

    /**
     * This method is executed before each test case to log that the case
     * is started using its name inside the logs.txt file, add new entry for
     * the test case to be executed inside the html report using its name
     * also to initialize the browser that will be used during the execution
     *
     * @param testResult to hold the data of the test case
     * @throws Exception in case of not being able to initialize the browser
     */
    @BeforeMethod
    public void beforeMethod(ITestResult testResult) throws Exception {
        var testMethodName = testResult.getMethod().getMethodName();

        log.info("---------------------------------------------------------------");
        log.info(String.format("Test Case Name: %s", testMethodName));
        log.info("Status: Started");
        log.info("---------------------------------------------------------------");

        test = report.startTest(testMethodName);
        initializeBrowser();
    }

    /**
     * This method is executed after each test case to log the final results
     * of the executed case inside both the log file or the html report, take a screenshot
     * in case of the test case has failed and save it to the html report,
     * also to close the current browser instance
     *
     * @param testResult to hold the data of the test case
     * @throws Exception in case of not being able to log the final results of the
     *                   executed case in both the log file and the html report,
     *                   not being able to take a screenshot in case of having failed
     *                   test case or not being able to close the current browser instance
     */
    @AfterMethod
    public void afterMethod(ITestResult testResult) throws Exception {
        logTestRunResults(testResult);
        closeDriver();
    }

    /**
     * This method is executed after all the test case to flush the report with all
     * the executed case results
     */
    @AfterSuite
    public void afterSuite() {
        report.flush();
    }

    /**
     * This method is executed after each test case to log the final results
     * of the executed case inside both the log file or the html report also to take
     * a screenshot in case of the test case has failed and save it to the html report
     *
     * @param testResult to hold the data of the test case
     * @throws Exception in case of not being able to log the final results of the
     *                   executed case in both the log file and the html report or
     *                   not being able to take a screenshot in case of having failed
     *                   test case
     */
    private void logTestRunResults(ITestResult testResult) throws Exception {
        var status = testResult.getStatus();
        var testMethodName = testResult.getMethod().getMethodName();

        switch (status) {
            case 1:
                logSuccess(String.format("Test Status: %s", "Passed"));
                break;
            case 2:
                var stackTrace = Arrays.toString(testResult.getThrowable().getStackTrace());
                var screenshotsPath = System.getProperty("user.dir")
                        + "/src/test/resources/testOutputs/screenshots/";

                logError(String.format("Stack Trace: %s", stackTrace));

                test.log(LogStatus.ERROR, "Check the following screenshot: "
                        + test.addBase64ScreenShot("data:image/png;charset=utf-8;base64, " +
                        ScreenshotUtil.takeScreenshot(driver
                                , screenshotsPath
                                , "Screenshot_" + testMethodName + ".png")));

                logFail(String.format("Test Status: %s", "Failed"));
                break;
            case 3:
                logSkip(String.format("Test Status: %s", "Skipped"));
                break;
            default:
                logUnknown(String.format("Test Status: %s", "Unknown"));
                break;
        }
    }
}
