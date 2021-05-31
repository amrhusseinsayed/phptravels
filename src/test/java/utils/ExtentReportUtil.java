package utils;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

import java.io.File;
import java.util.Properties;

public class ExtentReportUtil {
    /**
     * This method is used to initialize the html report that will be
     * used to log the test case executions
     *
     * @param properties the properties that should contain both paths
     *                   of the html report configuration xml file and
     *                   the path of the final report to be saved to
     * @return the extent report after being initialized
     */
    public static ExtentReports initializeHtmlReport(Properties properties) {
        var report = new ExtentReports(System.getProperty("user.dir")
                + properties.getProperty("htmlReportPath")
                , true, DisplayOrder.OLDEST_FIRST);

        report.loadConfig(new File(System.getProperty("user.dir")
                + properties.getProperty("htmlReportConfigPath")));

        return report;
    }
}
