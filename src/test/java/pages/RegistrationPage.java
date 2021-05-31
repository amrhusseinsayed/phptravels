package pages;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import utils.ActionsUtil;

public class RegistrationPage extends ActionsUtil {
    private static Logger log = Logger.getLogger(RegistrationPage.class);

    @CacheLookup
    @FindBy(name = "firstname")
    private WebElement firstName;

    @CacheLookup
    @FindBy(name = "lastname")
    private WebElement lastName;

    @CacheLookup
    @FindBy(name = "phone")
    private WebElement phone;

    @CacheLookup
    @FindBy(name = "email")
    private WebElement email;

    @CacheLookup
    @FindBy(name = "password")
    private WebElement password;

    @CacheLookup
    @FindBy(name = "confirmpassword")
    private WebElement confirmPassword;

    @CacheLookup
    @FindBy(xpath = "//*[contains(@class,'signupbtn')]")
    private WebElement signUp;

    public RegistrationPage(WebDriver driver, FluentWait<WebDriver> wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    /**
     * This method is used to fill the registration form with valid data
     *
     * @param testData the data of all the users as a JSON
     * @param user     the key of the user to extract his data from the users JSON
     * @return new instance from the Homepage
     */
    public HomePage fillFormWithValidData(JSONObject testData, String user) {
        fillForm(testData, user);
        return new HomePage(driver, wait);
    }

    /**
     * This method is used to fill the registration form with invalid data
     *
     * @param testData the data of all the users as a JSON
     * @param user     the key of the user to extract his data from the users JSON
     * @return the current instance of the page
     */
    public RegistrationPage fillFormWithInvalidData(JSONObject testData, String user) {
        fillForm(testData, user);
        return this;
    }

    /**
     * This method is used to fill the registration form
     *
     * @param testData the data of all the users as a JSON
     * @param user     the key of the user to extract his data from the users JSON
     */
    public void fillForm(JSONObject testData, String user) {
        JSONObject userData = (JSONObject) testData.get(user);
        Assert.assertNotNull(userData, String.format("Invalid user key: '%s'", user));

        addFirstName(userData.get("firstName").toString());
        addLastName(userData.get("lastName").toString());
        addMobileNumber(userData.get("mobileNumber").toString());
        addEmail(userData.get("email").toString());
        addPassword(userData.get("password").toString());
        addPasswordConfirmation(userData.get("passwordConfirmation").toString());
        signUp();
    }

    /**
     * This method is used to fill the First Name field with
     * the provided data
     *
     * @param firstNameStr the data to be added to the First Name field
     */
    public void addFirstName(String firstNameStr) {
        try {
            log.debug("Waiting for the First Name filed to be visible and enabled");
            addText(firstName, firstNameStr);
            logInfo(String.format("Entering First Name: '%s'", firstNameStr));
        } catch (Exception e) {
            logError("Exception while trying to add the First Name to the registration form");
            throw e;
        }
    }

    /**
     * This method is used to fill the Last Name field with
     * the provided data
     *
     * @param lastNameStr the data to be added to the Last Name field
     */
    public void addLastName(String lastNameStr) {
        try {
            log.debug("Waiting for the Last Name filed to be visible and enabled");
            addText(lastName, lastNameStr);
            logInfo(String.format("Entering Last Name: '%s'", lastNameStr));
        } catch (Exception e) {
            logError("Exception while trying to add the Last Name to the registration form");
            throw e;
        }
    }

    /**
     * This method is used to fill the Mobile Number field with
     * the provided data
     *
     * @param phoneStr the data to be added to the Mobile Number field
     */
    public void addMobileNumber(String phoneStr) {
        try {
            log.debug("Waiting for the Mobile Number filed to be visible and enabled");
            addText(phone, phoneStr);
            logInfo(String.format("Entering Mobile Number: '%s'", phoneStr));
        } catch (Exception e) {
            logError("Exception while trying to add the Mobile Number to the registration form");
            throw e;
        }
    }

    /**
     * This method is used to fill the Email field with
     * the provided data
     *
     * @param emailStr the data to be added to the Email field
     */
    public void addEmail(String emailStr) {
        try {
            log.debug("Waiting for the Email filed to be visible and enabled");
            addText(email, emailStr);
            logInfo(String.format("Entering Email: '%s'", emailStr));
        } catch (Exception e) {
            logError("Exception while trying to add the Email to the registration form");
            throw e;
        }
    }

    /**
     * This method is used to fill the Password field with
     * the provided data
     *
     * @param passwordStr the data to be added to the Password field
     */
    public void addPassword(String passwordStr) {
        try {
            log.debug("Waiting for the Password filed to be visible and enabled");
            addText(password, passwordStr);
            logInfo(String.format("Entering Password: '%s'", passwordStr));
        } catch (Exception e) {
            logError("Exception while trying to add the Password to the registration form");
            throw e;
        }
    }

    /**
     * This method is used to fill the Confirm Password field with
     * the provided data
     *
     * @param passwordConfirmationStr the data to be added to the
     *                                Confirm Password field
     */
    public void addPasswordConfirmation(String passwordConfirmationStr) {
        try {
            log.debug("Waiting for the Password Confirmation filed to be visible and enabled");
            addText(confirmPassword, passwordConfirmationStr);
            logInfo(String.format("Entering Password Confirmation: '%s'", passwordConfirmationStr));
        } catch (Exception e) {
            logError("Exception while trying to add the Password Confirmation to the registration form");
            throw e;
        }
    }

    /**
     * This method is used to click on the Sign Up button of the form
     */
    public void signUp() {
        try {
            log.debug("Waiting for the Sign Up button to be visible and enabled");
            clickOn(signUp, true);
            logInfo("Signing Up......");
        } catch (Exception e) {
            logError("Exception while trying to click on the Sign Up button of the registration form");
            throw e;
        }
    }

    /**
     * This method is used to wait for the given validation message
     * to be visible
     *
     * @param message the validation message to wait for
     */
    public void waitForValidationMessage(String message) {
        try {
            log.debug(String.format("Waiting for the validation message '%s' to be visible", message));
            waitForTextToBeVisible(message);
            logInfo(String.format("Registration Validation Message: '%s'", message));
        } catch (Exception e) {
            logError("Exception while waiting for the validation message to be visible");
            throw e;
        }
    }

    /**
     * This method is used to wait for the given validation message related
     * to the given field to be visible
     *
     * @param filedName the field that the validation should appear for
     * @param message   the validation message to appear
     * @throws Exception in case the message in not visible after the timeout
     */
    public void waitForMissingFiledValidationMessage(String filedName, String message) throws Exception {
        try {
            log.debug(String.format("Waiting for the validation message '%s' to be visible", message));
            var element = getElementByName(filedName);
            waitForElementAttributeValueToBe(element, "validationMessage", message);
            logInfo(String.format("Registration Validation Message: '%s'", message));
        } catch (Exception e) {
            logError("Exception while waiting for the validation message to be visible");
            throw e;
        }
    }

    /**
     * This method is used to get the WebElement of the given field name
     * in the form
     *
     * @param filedName the name of the field inside the form
     * @return the WebElement the matches the entered field name
     * @throws Exception in case the given field name does not exist in the form
     */
    private WebElement getElementByName(String filedName) throws Exception {
        switch (filedName) {
            case "First Name":
                return firstName;
            case "Last Name":
                return lastName;
            case "Mobile Number":
                return phone;
            case "Email":
                return email;
            case "Password":
                return password;
            case "Confirm Password":
                return confirmPassword;
            default:
                throw new Exception(String.format("Invalid field name: '%s'", filedName));
        }
    }
}
