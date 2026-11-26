package com.meghana.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenAccountPage extends BasePage {
    public OpenAccountPage(WebDriver driver) {
        super(driver);
    }

    public OpenAccountPage open() {
        clickMenu("Open New Account");
        return this;
    }

    /** type must be exactly CHECKING or SAVINGS. The first account is used for funding. */
    public void openAccount(String type) {
        waitForOptions(By.id("fromAccountId"), 1);
        new Select(visible(By.id("type"))).selectByVisibleText(type);
        click(By.cssSelector("input[value='Open New Account']"));
    }

    public boolean isOpened() {
        return waitForText("Account Opened!");
    }

    /** The new account number shown on the result page, or null if it is not found. */
    public String newAccountId() {
        Matcher m = Pattern.compile("account number:?\\s*(\\d+)", Pattern.CASE_INSENSITIVE).matcher(bodyText());
        return m.find() ? m.group(1) : null;
    }
}
