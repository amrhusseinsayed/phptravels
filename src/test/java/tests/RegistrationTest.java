package tests;

import org.testng.annotations.Test;
import pages.UserControl;

public class RegistrationTest extends BaseTest {
    private UserControl user;

    @Test()
    public void registerValidUser() {
        user = new UserControl(driver, wait, properties);

        user.navigateToRegistrationPage()
                .fillFormWithValidData(testData, "user1")
                .waitForUserRegistration();
    }

    @Test(priority = 1, dependsOnMethods = "registerValidUser")
    public void registerUserWithExistingEmail() {
        user = new UserControl(driver, wait, properties);

        user.navigateToRegistrationPage()
                .fillFormWithInvalidData(testData, "user1")
                .waitForValidationMessage("Email Already Exists.");
    }

    @Test()
    public void registerUserWithInvalidEmail() {
        user = new UserControl(driver, wait, properties);

        user.navigateToRegistrationPage()
                .fillFormWithInvalidData(testData, "user2")
                .waitForValidationMessage("The Email field must contain a valid email address.");
    }

    @Test()
    public void registerUserWithShortPassword() {
        user = new UserControl(driver, wait, properties);

        user.navigateToRegistrationPage()
                .fillFormWithInvalidData(testData, "user3")
                .waitForValidationMessage("The Password field must be at least 6 characters in length.");
    }

    @Test()
    public void registerUserWithUnmatchedPasswords() {
        user = new UserControl(driver, wait, properties);

        user.navigateToRegistrationPage()
                .fillFormWithInvalidData(testData, "user4")
                .waitForValidationMessage("Password not matching with confirm password.");
    }

    @Test()
    public void registerUserWithoutFirstName() throws Exception {
        user = new UserControl(driver, wait, properties);

        user.navigateToRegistrationPage()
                .fillFormWithInvalidData(testData, "user5")
                .waitForMissingFiledValidationMessage("First Name"
                        , "Please fill out this field.");
    }

    @Test()
    public void registerUserWithoutLastName() throws Exception {
        user = new UserControl(driver, wait, properties);

        user.navigateToRegistrationPage()
                .fillFormWithInvalidData(testData, "user6")
                .waitForMissingFiledValidationMessage("Last Name"
                        , "Please fill out this field.");
    }

    @Test()
    public void registerUserWithoutEmail() throws Exception {
        user = new UserControl(driver, wait, properties);

        user.navigateToRegistrationPage()
                .fillFormWithInvalidData(testData, "user7")
                .waitForMissingFiledValidationMessage("Email"
                        , "Please fill out this field.");
    }

    @Test()
    public void registerUserWithoutMobileNumber() throws Exception {
        user = new UserControl(driver, wait, properties);

        user.navigateToRegistrationPage()
                .fillFormWithInvalidData(testData, "user8")
                .waitForMissingFiledValidationMessage("Mobile Number"
                        , "Please fill out this field.");
    }

    @Test()
    public void registerUserWithoutPassword() throws Exception {
        user = new UserControl(driver, wait, properties);

        user.navigateToRegistrationPage()
                .fillFormWithInvalidData(testData, "user9")
                .waitForMissingFiledValidationMessage("Password"
                        , "Please fill out this field.");
    }

    @Test()
    public void registerUserWithoutPasswordConfirmation() throws Exception {
        user = new UserControl(driver, wait, properties);

        user.navigateToRegistrationPage()
                .fillFormWithInvalidData(testData, "user10")
                .waitForMissingFiledValidationMessage("Confirm Password"
                        , "Please fill out this field.");
    }
}
