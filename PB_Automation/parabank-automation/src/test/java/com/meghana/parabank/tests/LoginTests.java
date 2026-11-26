package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.HomePage;
import com.meghana.parabank.pages.OverviewPage;
import com.meghana.parabank.utils.Config;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    private static String swapCase(String text) {
        StringBuilder out = new StringBuilder();
        for (char c : text.toCharArray()) {
            out.append(Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c));
        }
        return out.toString();
    }

    @Test(groups = {"smoke", "regression"}, description = "PB_TC_007 | Login with valid credentials")
    public void PB_TC_007_loginWithValidCredentials() {
        HomePage home = new HomePage(driver).open();
        home.login(Config.username(), Config.password());

        OverviewPage overview = new OverviewPage(driver);
        Assert.assertTrue(overview.isLoaded(), "Accounts Overview should open after login. " + overview.debugInfo());
    }

    @Test(groups = "regression", description = "PB_TC_008 | Login with valid username and wrong password")
    public void PB_TC_008_loginWithInvalidCredentials() {
        HomePage home = new HomePage(driver).open();
        home.login(Config.username(), "Wrong@123");

        Assert.assertTrue(home.waitForText("could not be verified"), "Login error message not shown. " + home.debugInfo());
        Assert.assertFalse(home.isLoggedIn(), "User must not be logged in");
    }

    @Test(groups = "regression", description = "PB_TC_009 | Login with an invalid username")
    public void PB_TC_009_loginWithInvalidUsername() {
        HomePage home = new HomePage(driver).open();
        home.login("wrong_user_" + System.currentTimeMillis(), "Test@123");

        Assert.assertTrue(home.waitForText("could not be verified"), "Login error message not shown. " + home.debugInfo());
        Assert.assertFalse(home.isLoggedIn(), "User must not be logged in");
    }

    @Test(groups = "regression", description = "PB_TC_010 | Login with blank username and password")
    public void PB_TC_010_loginWithBlankCredentials() {
        HomePage home = new HomePage(driver).open();
        home.clickLoginWithBlankFields();

        Assert.assertTrue(home.waitForText("Please enter a username and password"),
                "Blank login message not shown. " + home.debugInfo());
        Assert.assertFalse(home.isLoggedIn(), "User must not be logged in");
    }

    @Test(groups = "regression", description = "PB_TC_012 | Password is case sensitive")
    public void PB_TC_012_passwordIsCaseSensitive() {
        String changed = swapCase(Config.password());
        if (changed.equals(Config.password())) {
            throw new SkipException("The password has no letters, so its case cannot be changed.");
        }
        HomePage home = new HomePage(driver).open();
        home.login(Config.username(), changed);

        Assert.assertTrue(home.waitForText("could not be verified"),
                "Login should fail for the wrong-case password. " + home.debugInfo());
        Assert.assertFalse(home.isLoggedIn(), "User must not be logged in");
    }

    @Test(groups = {"smoke", "regression"}, description = "PB_TC_013 | Log out returns to the login page")
    public void PB_TC_013_logout() {
        loginAsCustomer();
        new OverviewPage(driver).logout();

        Assert.assertTrue(new HomePage(driver).isLoginPanelShown(), "Customer Login panel should be shown after logout");
    }
}
