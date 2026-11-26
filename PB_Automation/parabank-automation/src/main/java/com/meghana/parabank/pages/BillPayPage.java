package com.meghana.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BillPayPage extends BasePage {
    public BillPayPage(WebDriver driver) {
        super(driver);
    }

    public BillPayPage open() {
        clickMenu("Bill Pay");
        visible(By.name("payee.name"));
        return this;
    }

    /** Fills the payee details (Bescom) with the given account numbers and amount. The first account pays. */
    public void fill(String account, String verifyAccount, String amount) {
        type(By.name("payee.name"), "Bescom");
        type(By.name("payee.address.street"), "5 Church Street");
        type(By.name("payee.address.city"), "Bengaluru");
        type(By.name("payee.address.state"), "Karnataka");
        type(By.name("payee.address.zipCode"), "560001");
        type(By.name("payee.phoneNumber"), "9876500000");
        type(By.name("payee.accountNumber"), account);
        type(By.name("verifyAccount"), verifyAccount);
        type(By.name("amount"), amount);
        selectFirst(By.name("fromAccountId"));
    }

    public void send() {
        click(By.cssSelector("input[value='Send Payment']"));
    }
}
