package com.meghana.parabank.pages;

import com.meghana.parabank.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(Config.homeUrl());
        visible(By.name("username"));
        return this;
    }

    public void login(String username, String password) {
        type(By.name("username"), username);
        type(By.name("password"), password);
        click(By.cssSelector("input[value='Log In']"));
    }

    public void clickLoginWithBlankFields() {
        click(By.cssSelector("input[value='Log In']"));
    }

    public void clickRegister() {
        click(By.linkText("Register"));
    }

    public boolean isLoginPanelShown() {
        return isShown(By.name("username")) && isShown(By.name("password"));
    }

    public String title() {
        return driver.getTitle();
    }

    /** Names of main home page elements that are not displayed. Empty list means everything is there. */
    public List<String> missingElements() {
        List<String> missing = new ArrayList<>();
        if (!isShown(By.name("username"))) {
            missing.add("Username box");
        }
        if (!isShown(By.name("password"))) {
            missing.add("Password box");
        }
        if (!isShown(By.cssSelector("input[value='Log In']"))) {
            missing.add("Log In button");
        }
        for (String link : new String[] {"Register", "Forgot login info?", "About Us", "Services", "Products", "Locations", "Admin Page"}) {
            if (!isShown(By.linkText(link))) {
                missing.add(link + " link");
            }
        }
        return missing;
    }
}
