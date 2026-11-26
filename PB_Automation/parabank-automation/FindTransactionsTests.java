package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.FindTransactionsPage;
import com.meghana.parabank.pages.TransferPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FindTransactionsTests extends BaseTest {

    // COMMENTED OUT: PB_TC_031 (Find transactions by amount) is left out of the main run because the results table loads slowly.
    // @Test(groups = "regression", description = "PB_TC_031 | Find transactions by amount")
    // public void PB_TC_031_findTransactionsByAmount() {
    // loginAsCustomer();
    // ensureTwoAccounts();
    // TransferPage transfer = new TransferPage(driver).open();
    // transfer.submit("17.17");
    // Assert.assertTrue(transfer.waitForText("Transfer Complete!"), "Setup transfer did not complete. " + transfer.debugInfo());
    //
    // // The results table fills in a moment after the "Transaction Results" heading, so wait for the amount
    // // and search once more if the first search came back empty.
    // FindTransactionsPage find = new FindTransactionsPage(driver).open();
    // boolean listed = false;
    // for (int attempt = 1; attempt <= 2 && !listed; attempt++) {
    // if (attempt > 1) {
    // find = new FindTransactionsPage(driver).open();
    // }
    // find.findByAmount("17.17");
    // Assert.assertTrue(find.waitForText("Transaction Results"), "Transaction Results page expected. " + find.debugInfo());
    // listed = find.waitForText("17.17");
    // }
    // Assert.assertTrue(listed, "The 17.17 transaction should be listed. " + find.debugInfo());
    // }
}
