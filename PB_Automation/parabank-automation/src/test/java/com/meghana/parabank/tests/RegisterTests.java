package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.HomePage;
import com.meghana.parabank.pages.RegisterPage;
import com.meghana.parabank.utils.UserData;
import org.testng.Assert;
import org.testng.annotations.Test;

/** If a 'verify you are human' box appears, tick it by hand. The waiting time is the 'timeout' in config.properties. */
public class RegisterTests extends BaseTest {

    private RegisterPage openRegisterPage() {
        HomePage home = new HomePage(driver).open();
        home.clickRegister();
        return new RegisterPage(driver).waitUntilLoaded();
    }

    @Test(groups = "regression", description = "PB_TC_001 | Register a new customer")
    public void PB_TC_001_registerNewCustomer() {
        RegisterPage register = openRegisterPage();
        UserData user = UserData.newCustomer();
        register.fill(user);
        register.submit();

        Assert.assertTrue(register.waitForTextPatiently("Your account was created successfully"),
                "Success message not shown. " + register.debugInfo());
        Assert.assertTrue(register.bodyText().contains("Welcome " + user.username()),
                "Welcome message with the username should be shown. " + register.debugInfo());
        Assert.assertTrue(register.isLoggedIn(), "Customer should be logged in after registration");
    }

    @Test(groups = "regression", description = "PB_TC_002 | Registration with all fields blank")
    public void PB_TC_002_registerWithBlankFields() {
        RegisterPage register = openRegisterPage();
        register.submit();

        Assert.assertTrue(register.waitForTextPatiently("is required"),
                "'... is required.' messages should be shown. " + register.debugInfo());
        Assert.assertFalse(register.isLoggedIn(), "No customer should be created");
    }

    @Test(groups = "regression", description = "PB_TC_003 | Registration with mismatched Password and Confirm password")
    public void PB_TC_003_registerPasswordMismatch() {
        RegisterPage register = openRegisterPage();
        register.fill(UserData.withUsername("tester_" + System.currentTimeMillis(), "Test@123", "Test@124"));
        register.submit();

        Assert.assertTrue(register.waitForTextPatiently("did not match"),
                "'Passwords did not match.' should be shown. " + register.debugInfo());
        Assert.assertFalse(register.isLoggedIn(), "No customer should be created");
    }

    /**
     * Registers a throw-away customer, logs out, then tries to register the same username again.
     * If the second registration succeeds, the site accepts duplicate usernames: check by hand and log a defect.
     */
    @Test(groups = "regression", description = "PB_TC_004 | Registration with an already existing username")
    public void PB_TC_004_registerExistingUsername() {
        String username = "dup_" + System.currentTimeMillis();
        RegisterPage first = openRegisterPage();
        first.fill(UserData.withUsername(username, "Test@123", "Test@123"));
        first.submit();
        Assert.assertTrue(first.waitForTextPatiently("Your account was created successfully"),
                "Setup failed: first registration did not work. " + first.debugInfo());
        first.logout();

        RegisterPage second = openRegisterPage();
        second.fill(UserData.withUsername(username, "Test@123", "Test@123"));
        second.submit();

        Assert.assertTrue(second.waitForTextPatiently("already exists"),
                "'This username already exists.' should be shown. The second registration with the same username was "
                + "probably accepted (possible defect). " + second.debugInfo());
    }
}
