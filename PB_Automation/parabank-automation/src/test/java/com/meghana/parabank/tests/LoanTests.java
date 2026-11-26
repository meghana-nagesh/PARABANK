package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.LoanPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoanTests extends BaseTest {

    @Test(groups = "regression", description = "PB_TC_036 | Request a loan with valid details")
    public void PB_TC_036_requestLoan() {
        loginAsCustomer();
        LoanPage loan = new LoanPage(driver).open();
        loan.apply("1000", "100");

        Assert.assertTrue(loan.waitForText("Loan Request Processed"), "Loan result page expected. " + loan.debugInfo());
        String text = loan.bodyText();
        Assert.assertTrue(text.contains("Approved") || text.contains("Denied"),
                "Loan status (Approved or Denied) should be shown. " + loan.debugInfo());
    }

    @Test(groups = "regression", description = "PB_TC_038 | Loan is denied when the down payment is more than the balance")
    public void PB_TC_038_loanDeniedForHighDownPayment() {
        loginAsCustomer();
        LoanPage loan = new LoanPage(driver).open();
        loan.apply("1000", "99999999");

        Assert.assertTrue(loan.waitForText("Loan Request Processed"), "Loan result page expected. " + loan.debugInfo());
        Assert.assertTrue(loan.bodyText().contains("Denied"), "Loan should be Denied. " + loan.debugInfo());
    }
}
