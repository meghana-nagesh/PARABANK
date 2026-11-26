package com.meghana.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OverviewPage extends BasePage {
    private final By accountLinks = By.cssSelector("#accountTable a[href*='activity.htm']");

    public OverviewPage(WebDriver driver) {
        super(driver);
    }

    public OverviewPage open() {
        clickMenu("Accounts Overview");
        return this;
    }

    public boolean isLoaded() {
        return waitForHeading("Accounts Overview");
    }

    /** Waits only 3 seconds. Use when the page should NOT open. */
    public boolean isLoadedQuickly() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(d -> !d.findElements(
                    By.xpath("//h1[contains(normalize-space(),'Accounts Overview')]")).isEmpty());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Number of accounts. The table is filled a moment after the page heading, so it waits for the first account. */
    public int accountCount() {
        try {
            quick.until(d -> !d.findElements(accountLinks).isEmpty());
        } catch (TimeoutException e) {
            return 0;
        }
        return driver.findElements(accountLinks).size();
    }

    public boolean hasColumnHeadings() {
        String text = bodyText();
        return text.contains("Account") && text.contains("Balance*") && text.contains("Available Amount");
    }

    public boolean hasTotalRow() {
        return isShown(By.xpath("//table[@id='accountTable']//b[normalize-space()='Total']"));
    }

    /** True if the account number appears on the page. Waits, because the table is filled after the heading. */
    public boolean listsAccount(String accountId) {
        return waitForText(accountId);
    }

    /** Balance of one account, for example 515.5 for '$515.50'. */
    public double balanceOf(String accountId) {
        String cell = visible(By.xpath("//table[@id='accountTable']//a[normalize-space()='" + accountId
                + "']/ancestor::tr/td[2]")).getText();
        return Double.parseDouble(cell.replaceAll("[^0-9.\\-]", ""));
    }
}
