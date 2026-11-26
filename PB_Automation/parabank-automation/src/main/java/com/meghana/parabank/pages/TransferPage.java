package com.meghana.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class TransferPage extends BasePage {
    private final By fromAccount = By.id("fromAccountId");
    private final By toAccount = By.id("toAccountId");
    private final By result = By.id("showResult");
    private final By error = By.id("showError");

    public TransferPage(WebDriver driver) {
        super(driver);
    }

    public TransferPage open() {
        clickMenu("Transfer Funds");
        return this;
    }

    /** Account numbers in the From dropdown, in order. Needs at least 2 accounts. */
    public List<String> accountIds() {
        waitForOptions(fromAccount, 2);
        List<String> ids = new ArrayList<>();
        for (WebElement option : new Select(driver.findElement(fromAccount)).getOptions()) {
            ids.add(option.getText().trim());
        }
        return ids;
    }

    /** Transfers from the first account to the second account. */
    public void submit(String amount) {
        waitForOptions(fromAccount, 1);
        waitForOptions(toAccount, 2);
        type(By.id("amount"), amount);
        new Select(driver.findElement(fromAccount)).selectByIndex(0);
        new Select(driver.findElement(toAccount)).selectByIndex(1);
        click(By.cssSelector("input[value='Transfer']"));
    }

    /** Waits until either the result or the error box is shown. */
    public boolean waitForOutcome() {
        try {
            quick.until(d -> isShown(result) || isShown(error));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isResultShown() {
        return isShown(result);
    }

    public boolean isErrorShown() {
        return isShown(error);
    }

    /** Text of the red error box, or an empty text if there is none. */
    public String errorText() {
        return driver.findElements(error).isEmpty() ? "" : driver.findElement(error).getText().replace("\n", " | ");
    }
}
