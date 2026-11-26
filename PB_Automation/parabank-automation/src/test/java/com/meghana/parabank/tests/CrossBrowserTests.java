package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.OverviewPage;
import com.meghana.parabank.pages.TransferPage;
import com.meghana.parabank.utils.Config;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Run in another browser:  mvn test -DsuiteFile=testng-crossbrowser.xml -Dbrowser=firefox  (or edge) */
public class CrossBrowserTests extends BaseTest {

    @Test(groups = "crossbrowser", description = "PB_TC_054 | Login and transfer work in another browser")
    public void PB_TC_054_mainFlowInAnotherBrowser() {
        loginAsCustomer();
        ensureTwoAccounts();
        TransferPage transfer = new TransferPage(driver).open();
        transfer.submit("20");

        Assert.assertTrue(transfer.waitForText("Transfer Complete!"),
                "Transfer did not complete in browser '" + Config.browser() + "'. " + transfer.debugInfo());
        new OverviewPage(driver).logout();
    }
}
