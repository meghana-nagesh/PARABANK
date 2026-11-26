package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.FindTransactionsPage;
import com.meghana.parabank.pages.TransferPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FindTransactionsTests extends BaseTest {

	@Test(enabled = false, groups = "regression", description = "PB_TC_031 | Find transactions by amount")    public void PB_TC_031_findTransactionsByAmount() {
        loginAsCustomer();
        ensureTwoAccounts();
        TransferPage transfer = new TransferPage(driver).open();
        transfer.submit("17.17");
        Assert.assertTrue(transfer.waitForText("Transfer Complete!"), "Setup transfer did not complete. " + transfer.debugInfo());

        FindTransactionsPage find = new FindTransactionsPage(driver).open();
        find.findByAmount("17.17");

        Assert.assertTrue(find.waitForText("Transaction Results"), "Transaction Results page expected. " + find.debugInfo());
        Assert.assertTrue(find.bodyText().contains("17.17"), "The 17.17 transaction should be listed. " + find.debugInfo());
    }
}
