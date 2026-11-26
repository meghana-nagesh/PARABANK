package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.BillPayPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BillPayTests extends BaseTest {

    @Test(groups = {"smoke", "regression"}, description = "PB_TC_025 | Pay a bill with valid payee details")
    public void PB_TC_025_billPay() {
        loginAsCustomer();
        BillPayPage billPay = new BillPayPage(driver).open();
        billPay.fill("54321", "54321", "25");
        billPay.send();

        Assert.assertTrue(billPay.waitForText("Bill Payment Complete"), "Payment did not complete. " + billPay.debugInfo());
        Assert.assertTrue(billPay.bodyText().contains("Bill Payment to Bescom in the amount of $25.00"),
                "Result message should show Bescom and $25.00. " + billPay.debugInfo());
    }

    @Test(groups = "regression", description = "PB_TC_026 | Bill payment with all fields blank")
    public void PB_TC_026_billPayBlankFields() {
        loginAsCustomer();
        BillPayPage billPay = new BillPayPage(driver).open();
        billPay.send();

        Assert.assertTrue(billPay.waitForText("required"), "'... is required.' messages expected. " + billPay.debugInfo());
    }

    @Test(groups = "regression", description = "PB_TC_027 | Bill payment when Account # and Verify Account # do not match")
    public void PB_TC_027_billPayAccountMismatch() {
        loginAsCustomer();
        BillPayPage billPay = new BillPayPage(driver).open();
        billPay.fill("54321", "54322", "25");
        billPay.send();

        Assert.assertTrue(billPay.waitForText("do not match"),
                "'The account numbers do not match.' expected. " + billPay.debugInfo());
    }
}
