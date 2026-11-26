package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.OverviewPage;
import com.meghana.parabank.utils.Config;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SecurityTests extends BaseTest {

    @Test(groups = "regression", description = "PB_TC_050 | Account page cannot be opened without logging in")
    public void PB_TC_050_accountPageNotOpenWithoutLogin() {
        driver.get(Config.ROOT + "/overview.htm");
        OverviewPage overview = new OverviewPage(driver);

        Assert.assertFalse(overview.isLoadedQuickly(), "Accounts Overview must not open without login. " + overview.debugInfo());
        Assert.assertFalse(overview.isLoggedIn(), "No customer should be logged in");
    }
}
