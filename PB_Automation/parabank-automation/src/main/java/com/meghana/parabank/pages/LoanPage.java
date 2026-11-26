package com.meghana.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoanPage extends BasePage {
    public LoanPage(WebDriver driver) {
        super(driver);
    }

    public LoanPage open() {
        clickMenu("Request Loan");
        return this;
    }

    public void apply(String loanAmount, String downPayment) {
        type(By.id("amount"), loanAmount);
        type(By.id("downPayment"), downPayment);
        selectFirst(By.id("fromAccountId"));
        click(By.cssSelector("input[value='Apply Now']"));
    }
}
