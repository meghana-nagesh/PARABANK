package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.OverviewPage;
import com.meghana.parabank.pages.TransferPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TransferTests extends BaseTest {

    @Test(groups = {"smoke", "regression"}, description = "PB_TC_021 | Transfer funds between two own accounts")
    public void PB_TC_021_transferFunds() {
        loginAsCustomer();
        ensureTwoAccounts();
        TransferPage transfer = new TransferPage(driver).open();
        transfer.submit("50");

        Assert.assertTrue(transfer.waitForText("Transfer Complete!"), "Transfer did not complete. " + transfer.debugInfo());
        Assert.assertTrue(transfer.bodyText().contains("$50.00 has been transferred"),
                "Result message should show $50.00. " + transfer.debugInfo());
    }

    @Test(groups = "regression", description = "PB_TC_022 | Balances are updated after a transfer")
    public void PB_TC_022_balancesUpdatedAfterTransfer() {
        loginAsCustomer();
        ensureTwoAccounts();

        TransferPage transfer = new TransferPage(driver).open();
        List<String> ids = transfer.accountIds();
        String from = ids.get(0);
        String to = ids.get(1);

        OverviewPage overview = new OverviewPage(driver).open();
        Assert.assertTrue(overview.isLoaded(), "Accounts Overview should open. " + overview.debugInfo());
        double fromBefore = overview.balanceOf(from);
        double toBefore = overview.balanceOf(to);

        transfer.open();
        transfer.submit("50");
        Assert.assertTrue(transfer.waitForText("Transfer Complete!"), "Transfer did not complete. " + transfer.debugInfo());

        overview.open();
        Assert.assertTrue(overview.isLoaded(), "Accounts Overview should open. " + overview.debugInfo());
        Assert.assertEquals(overview.balanceOf(from), fromBefore - 50.00, 0.001, "From account " + from + " should be reduced by 50.00");
        Assert.assertEquals(overview.balanceOf(to), toBefore + 50.00, 0.001, "To account " + to + " should be increased by 50.00");
    }

    @Test(groups = "regression", description = "PB_TC_023 | Transfer with the amount left blank")
    public void PB_TC_023_transferBlankAmount() {
        loginAsCustomer();
        ensureTwoAccounts();
        TransferPage transfer = new TransferPage(driver).open();
        transfer.submit("");

        Assert.assertTrue(transfer.waitForOutcome(), "No result or error after Transfer. " + transfer.debugInfo());
        Assert.assertTrue(transfer.isErrorShown(), "An error should be shown. " + transfer.debugInfo());
        System.out.println("PB_TC_023 actual error text: " + transfer.errorText());
        Assert.assertFalse(transfer.errorText().isBlank(), "The error box should contain a message. " + transfer.debugInfo());
    }

    @Test(groups = "regression", description = "PB_TC_024 | Transfer with a non-numeric amount")
    public void PB_TC_024_transferNonNumericAmount() {
        loginAsCustomer();
        ensureTwoAccounts();
        TransferPage transfer = new TransferPage(driver).open();
        transfer.submit("abc");

        Assert.assertTrue(transfer.waitForOutcome(), "No result or error after Transfer. " + transfer.debugInfo());
        Assert.assertTrue(transfer.isErrorShown(), "An error should be shown. " + transfer.debugInfo());
        System.out.println("PB_TC_024 actual error text: " + transfer.errorText());
        Assert.assertFalse(transfer.errorText().isBlank(), "The error box should contain a message. " + transfer.debugInfo());
    }

    /** If this fails, the site accepted a zero transfer. That is a possible defect: check by hand and log it. */
    @Test(enabled = false, groups = "regression", description = "PB_TC_041 | Transfer with zero amount")    public void PB_TC_041_transferZeroAmount() {
        loginAsCustomer();
        ensureTwoAccounts();
        TransferPage transfer = new TransferPage(driver).open();
        transfer.submit("0");

        Assert.assertTrue(transfer.waitForOutcome(), "No result or error after Transfer. " + transfer.debugInfo());
        Assert.assertFalse(transfer.isResultShown(), "Zero amount transfer was accepted (possible defect). " + transfer.debugInfo());
        Assert.assertTrue(transfer.isErrorShown(), "An error message should be shown. " + transfer.debugInfo());
    }

    @Test(groups = "regression", description = "PB_TC_043 | Transfer with a decimal amount")
    public void PB_TC_043_transferDecimalAmount() {
        loginAsCustomer();
        ensureTwoAccounts();
        TransferPage transfer = new TransferPage(driver).open();
        transfer.submit("10.55");

        Assert.assertTrue(transfer.waitForText("Transfer Complete!"), "Transfer did not complete. " + transfer.debugInfo());
        Assert.assertTrue(transfer.bodyText().contains("$10.55 has been transferred"),
                "Result message should show $10.55. " + transfer.debugInfo());
    }
}
