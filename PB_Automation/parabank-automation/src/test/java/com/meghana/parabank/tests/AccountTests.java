package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.OpenAccountPage;
import com.meghana.parabank.pages.OverviewPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountTests extends BaseTest {

    @Test(groups = "regression", description = "PB_TC_016 | Accounts Overview shows the table and the total")
    public void PB_TC_016_accountsOverviewTable() {
        loginAsCustomer();
        OverviewPage overview = new OverviewPage(driver).open();

        Assert.assertTrue(overview.isLoaded(), "Accounts Overview should open. " + overview.debugInfo());
        Assert.assertTrue(overview.hasColumnHeadings(), "Account, Balance* and Available Amount headings expected. " + overview.debugInfo());
        Assert.assertTrue(overview.accountCount() >= 1, "At least one account should be listed");
        Assert.assertTrue(overview.hasTotalRow(), "Total row should be shown");
    }

    private String openAccountAndCheck(String type) {
        OpenAccountPage openAccount = new OpenAccountPage(driver).open();
        openAccount.openAccount(type);

        Assert.assertTrue(openAccount.isOpened(), "'Account Opened!' not shown. " + openAccount.debugInfo());
        Assert.assertTrue(openAccount.bodyText().contains("Congratulations"), "Congratulations message expected. " + openAccount.debugInfo());
        String id = openAccount.newAccountId();
        Assert.assertNotNull(id, "New account number not found. " + openAccount.debugInfo());
        return id;
    }

    @Test(groups = "regression", description = "PB_TC_018 | Open a new SAVINGS account")
    public void PB_TC_018_openSavingsAccount() {
        loginAsCustomer();
        openAccountAndCheck("SAVINGS");
    }

    @Test(groups = {"smoke", "regression"}, description = "PB_TC_019 | Open a new CHECKING account")
    public void PB_TC_019_openCheckingAccount() {
        loginAsCustomer();
        openAccountAndCheck("CHECKING");
    }

    @Test(groups = "regression", description = "PB_TC_020 | The newly opened account is listed in Accounts Overview")
    public void PB_TC_020_newAccountListed() {
        loginAsCustomer();
        String newId = openAccountAndCheck("SAVINGS");

        OverviewPage overview = new OverviewPage(driver).open();
        Assert.assertTrue(overview.isLoaded(), "Accounts Overview should open. " + overview.debugInfo());
        Assert.assertTrue(overview.listsAccount(newId), "New account " + newId + " should be listed. " + overview.debugInfo());
    }
}
