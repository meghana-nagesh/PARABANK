package com.meghana.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FindTransactionsPage extends BasePage {
    public FindTransactionsPage(WebDriver driver) {
        super(driver);
    }

    public FindTransactionsPage open() {
        clickMenu("Find Transactions");
        return this;
    }

    /** Searches the first account for transactions with this amount. */
    public void findByAmount(String amount) {
        selectFirst(By.id("accountId"));
        type(By.id("amount"), amount);
        click(By.id("findByAmount"));
    }
}
