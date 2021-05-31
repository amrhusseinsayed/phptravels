
# phptravels
phptravels is a Java testing framework built with TestNG to cover the testing of the Registration form of the website and it can be used later to cover more forms of the site.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for testing purposes.

## Concepts Included
* Testing Framework design
* Page Object pattern
* Page Factory
* Common web page interaction methods
* Extracting test data from json file
* Html reports for testing outputs
* Screenshots for the testing outputs

## Tools
* Maven
* Selenium WebDriver
* TestNG

## Requirements
In order to utilize this project, you need to have the following installed locally:
* Maven
* Chrome version 90.0.4430.212
* Java Compiler 11
* TestNG
## Installing
* Install Java
* Set the System Environment variables with JDK and JRE paths
* Install Eclipse or IntelliJ
* Install TestNG inside the IDE if not configured by default
* Install Maven inside the IDE if not configured by default
* Install Git
* Clone the project from `https://github.com/amrhusseinsayed/phptravels.git`

## Framework structure
* The directory `src/test/java/testOutputs` will hold the html report of the status of the executed cases also a folder for the screenshots taken during the execution
* `config.properties` in the directory `src/test/java/configurations` holds the properties that will be used during the execution
* `extent-config.xml` in the directory `src/test/java/configurations` holds the configurations of the html report that will hold all the results of the executed cases
* `log4j.xml` in the directory `src/test/java/configurations` holds the configurations of the logger that will be used during the execution
* `chromedriver.exe` in the directory `src/test/java/drivers` is the WebDriver used during the execution
* `users.json` in the directory `src/test/java/testData` is the test data used during the execution
* `ExtentReportUtil.java` in the directory `src/test/java/utils` holds the methods used to initialize the Html report that will be used to collect the executions results
* `ActionsUtil.java` in the directory `src/test/java/utils` holds the helper methods that can be used during the execution
* `JsonFileUtil.java` in the directory `src/test/java/utils` holds a method to parse any JSON file to an actual JSON
* `Log4jUtil` in the directory `src/test/java/utils` holds a method to load the configurations of the logs file
* `PropertiesFileUtil.java` in the directory `src/test/java/utils` holds a method to read any properties file
* `ScreenshotUtil.java` in the directory `src/test/java/utils` holds a method to take a screenshot from the current view
* `UserControl.java` in the directory `src/test/java/pages` holds a method to navigate to the Registration form
* `RegistrationPage.java` in the directory `src/test/java/pages` holds the methods of filling the registeration form either with valid or invalid data
* `HomePage.java` in the directory `src/test/java/pages` holds a method to assert that whenever a new user is registered, he will be automatically directed to the Homepage
* `BaseTest.java` in the directory `src/test/java/tests` which inherits the `ActionsUtil.java` class holds the methods to run before suite starts and after the suits ends also a method to run before each test case and another one to run after each case
*   `RegistrationTest.java` in the directory `src/test/java/tests` holds the following test cases 
    * registerValidUser: to test the registration form with valid data
    * registerUserWithExistingEmail: to test the registration form with an existing email
    * registerUserWithInvalidEmail: to test the registration form with an invalid email
    * registerUserWithShortPassword: to test the registration form with short password
    * registerUserWithUnmatchedPasswords: to test the registration form with unmatched passwords
    * registerUserWithoutFirstName: to test the registration form without the data of the First Name field
    * registerUserWithoutLastName: to test the registration form without the data of the Last Name field
    * registerUserWithoutEmail: to test the registration form without the data of the Email field
    * registerUserWithoutMobileNumber: to test the registration form without the data of the Mobile Number field
    * registerUserWithoutPassword: to test the registration form without the data of the Password field
    * registerUserWithoutPasswordConfirmation: to test the registration form without the data of the Confirm Password field

## Running the tests
* Navigat to `src/test/resources/testData` directory and open `users.json` file
* Set the data of the users as required and make sure the `user1` has an email address that has not been registered before
* Run `RegistrationTest.java` in the directory `src/test/java/tests` as TestNG class to run all the test cases or you can select the desired method from the class to run 

## Running workflow description
* The execution begins from the method under the annotaion `@BeforeSuite` inside the `BaseTest.java` and it does the following
  * Extract the properties from the `config.properties` in the directory `src/test/java/configurations` that will be used during the execution
  * Extract the test data from `users.json` in the directory `src/test/java/testData` that will be used during the execution
  * Initialize the Extent Report using its configurations xml file
  * Initialize the Logger using its configurations xml file
* The method `beforeMethod` inside the same class is executed to add an entry for the case to be executed inside the html report also to initialize a browser instance based on the properties extracted in the previous step
* Then the desired test case is executed
* The method `afterMethod` inside `BaseTest.java` to log the final results of the executed test case inside both the log file and the html report also to take screenshot if the case fails, finally, it closes the current browser instance
* If the execution contains more than one case, they will be executed sequentially based on their order
* Finally, the method under the annotation `@AfterSuite` inside the `BaseTest.java` flush the data of the executed cases inside the html report

## Reporting
You can see a sample of the testing report, logs files and screenshots in the directory `src/test/resources/testOutputs` which contains
* `screenshots` folder which contains a screenshot for a failed scenario that has name matches to the test case method name
* `html-report-all-scenarios-passed.html` an html report that has all the cases passed
* `html-report-with-failed-scenario.html` an html report that has all the cases passed except one failed scenario
* `logfile-with-all-scenarios-passed.txt` logs file that has all the cases passed
* `logfile-with-failed-scenario.txt` logs file that has all the cases passed except one failed scenario

## Notes
* Make sure that the Java Complier used during the run is 11 or higher
* The `ActionsUtil.java` class does not contain all the methods used to interact with the web page but only the methods used during the execution, so it can be enhanced later if needed
