package com.meghana.parabank.pages;

import com.meghana.parabank.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/** Common actions for every page class. All waiting happens here, never with Thread.sleep. */
public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;    // long wait: page loads, login (time to tick a human check)
    protected final WebDriverWait quick;   // short wait: result right after a click

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.timeout()));
        this.quick = new WebDriverWait(driver, Duration.ofSeconds(Config.quick()));
        // When ParaBank loads a new page, an element we just found can disappear (stale element).
        // The waits must try again instead of failing the test.
        this.wait.ignoring(StaleElementReferenceException.class);
        this.quick.ignoring(StaleElementReferenceException.class);
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void type(By locator, String text) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected boolean isShown(By locator) {
        for (WebElement e : driver.findElements(locator)) {
            if (e.isDisplayed()) {
                return true;
            }
        }
        return false;
    }

    protected void waitForOptions(By dropdown, int minimum) {
        wait.until(d -> new Select(d.findElement(dropdown)).getOptions().size() >= minimum);
    }

    protected void selectFirst(By dropdown) {
        waitForOptions(dropdown, 1);
        new Select(driver.findElement(dropdown)).selectByIndex(0);
    }

    public String bodyText() {
        return driver.findElement(By.tagName("body")).getText();
    }

    /** URL and the text of the main panel (where ParaBank shows results and errors). Put it in assertion messages. */
    public String debugInfo() {
        List<WebElement> panel = driver.findElements(By.id("rightPanel"));
        String text = (panel.isEmpty() ? bodyText() : panel.get(0).getText()).replace("\n", " | ");
        return "URL: " + driver.getCurrentUrl() + " | Page text: " + (text.length() > 400 ? text.substring(0, 400) : text);
    }

    /** True if the text (not case sensitive) appears on the page within the short wait. */
    public boolean waitForText(String text) {
        return waitForText(text, quick);
    }

    /** Same, but with the long wait. Use after a click that may show a 'verify you are human' box. */
    public boolean waitForTextPatiently(String text) {
        return waitForText(text, wait);
    }

    private boolean waitForText(String text, WebDriverWait waiter) {
        try {
            waiter.until(d -> bodyText().toLowerCase().contains(text.toLowerCase()));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private String headings() {
        StringBuilder out = new StringBuilder();
        for (WebElement e : driver.findElements(By.cssSelector("h1"))) {
            if (e.isDisplayed()) {
                out.append(e.getText()).append(' ');
            }
        }
        return out.toString().toLowerCase();
    }

    /** True if a page heading contains the text (not case sensitive) within the long wait. */
    public boolean waitForHeading(String text) {
        try {
            wait.until(d -> headings().contains(text.toLowerCase()));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ---- Left menu (same on every page after login) ----
    public void clickMenu(String linkText) {
        click(By.linkText(linkText));
    }

    public boolean isLoggedIn() {
        return !driver.findElements(By.linkText("Log Out")).isEmpty();
    }

    public void logout() {
        click(By.linkText("Log Out"));
        visible(By.name("username"));
    }
}