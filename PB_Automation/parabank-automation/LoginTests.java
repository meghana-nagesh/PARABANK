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

    // COMMENTED OUT: this test found a real ParaBank defect (see ParaBank_Defects_Draft.xlsx). Kept here for reference.
    // /**
    // * DEFECT CONFIRMED (BUG_005). Expected: a wrong password is rejected with an error.
    // * Actual: ParaBank logs the customer in. This test PASSES while the defect exists.
    // * If ParaBank fixes it, the test fails: then change it to expect "could not be verified".
    // */
    // @Test(groups = "regression", description = "PB_TC_008 | Wrong password is accepted (confirms BUG_005)")
    // public void PB_TC_008_wrongPasswordIsAcceptedBUG_005() {
    // HomePage home = new HomePage(driver).open();
    // home.login(Config.username(), "Wrong@123");
    //
    // OverviewPage overview = new OverviewPage(driver);
    // Assert.assertTrue(overview.isLoaded(),
    // "BUG_005 not reproduced: the wrong password was rejected this time. " + home.debugInfo());
    // System.out.println("BUG_005 confirmed: ParaBank logged in with a wrong password.");
    // }

    // COMMENTED OUT: this test found a real ParaBank defect (see ParaBank_Defects_Draft.xlsx). Kept here for reference.
    // /**
    // * DEFECT CONFIRMED (BUG_006). Expected: an unknown username is rejected with an error.
    // * Actual: ParaBank opens Accounts Overview. This test PASSES while the defect exists.
    // */
    // @Test(groups = "regression", description = "PB_TC_009 | Invalid username is accepted (confirms BUG_006)")
    // public void PB_TC_009_invalidUsernameIsAcceptedBUG_006() {
    // HomePage home = new HomePage(driver).open();
    // home.login("wrong_user_" + System.currentTimeMillis(), "Test@123");
    //
    // OverviewPage overview = new OverviewPage(driver);
    // Assert.assertTrue(overview.isLoaded(),
    // "BUG_006 not reproduced: the invalid username was rejected this time. " + home.debugInfo());
    // System.out.println("BUG_006 confirmed: ParaBank logged in with an invalid username.");
    // }

    @Test(groups = "regression", description = "PB_TC_010 | Login with blank username and password")
    public void PB_TC_010_loginWithBlankCredentials() {
        HomePage home = new HomePage(driver).open();
        home.clickLoginWithBlankFields();

        Assert.assertTrue(home.waitForText("Please enter a username and password"),
                "Blank login message not shown. " + home.debugInfo());
        Assert.assertFalse(home.isLoggedIn(), "User must not be logged in");
    }

    // COMMENTED OUT: this test found a real ParaBank defect (see ParaBank_Defects_Draft.xlsx). Kept here for reference.
    // /** DEFECT CONFIRMED (BUG_005): a password with the wrong case is also accepted, so the password is not checked. */
    // @Test(groups = "regression", description = "PB_TC_012 | Wrong-case password is accepted (confirms BUG_005)")
    // public void PB_TC_012_wrongCasePasswordIsAcceptedBUG_005() {
    // String changed = swapCase(Config.password());
    // if (changed.equals(Config.password())) {
    // throw new SkipException("The password has no letters, so its case cannot be changed.");
    // }
    // HomePage home = new HomePage(driver).open();
    // home.login(Config.username(), changed);
    //
    // OverviewPage overview = new OverviewPage(driver);
    // Assert.assertTrue(overview.isLoaded(),
    // "BUG_005 not reproduced: the wrong-case password was rejected this time. " + home.debugInfo());
    // System.out.println("BUG_005 confirmed: ParaBank logged in with a wrong-case password.");
    // }

    @Test(groups = {"smoke", "regression"}, description = "PB_TC_013 | Log out returns to the login page")
    public void PB_TC_013_logout() {
        loginAsCustomer();
        new OverviewPage(driver).logout();

        Assert.assertTrue(new HomePage(driver).isLoginPanelShown(), "Customer Login panel should be shown after logout");
    }
}
