package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.OverviewPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationTests extends BaseTest {

    @Test(groups = {"smoke", "regression"}, description = "PB_TC_039 | All left menu links open the correct pages")
    public void PB_TC_039_leftMenuLinks() {
        loginAsCustomer();
        OverviewPage page = new OverviewPage(driver);

        String[][] links = {
                {"Open New Account", "Open New Account"},
                {"Accounts Overview", "Accounts Overview"},
                {"Transfer Funds", "Transfer Funds"},
                {"Bill Pay", "Bill Payment Service"},
                {"Find Transactions", "Find Transactions"},
                {"Update Contact Info", "Update Profile"},
                {"Request Loan", "Apply for a Loan"}
        };
        for (String[] link : links) {
            page.clickMenu(link[0]);
            Assert.assertTrue(page.waitForHeading(link[1]),
                    "Link '" + link[0] + "' should open the '" + link[1] + "' page. " + page.debugInfo());
        }
    }
}
