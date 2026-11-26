package com.meghana.parabank.pages;

import com.meghana.parabank.utils.UserData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    /** Waits for the registration form (a 'verify you are human' box may need a manual tick first). */
    public RegisterPage waitUntilLoaded() {
        visible(By.id("customer.firstName"));
        return this;
    }

    public void fill(UserData u) {
        type(By.id("customer.firstName"), u.firstName());
        type(By.id("customer.lastName"), u.lastName());
        type(By.id("customer.address.street"), u.address());
        type(By.id("customer.address.city"), u.city());
        type(By.id("customer.address.state"), u.state());
        type(By.id("customer.address.zipCode"), u.zip());
        type(By.id("customer.phoneNumber"), u.phone());
        type(By.id("customer.ssn"), u.ssn());
        type(By.id("customer.username"), u.username());
        type(By.id("customer.password"), u.password());
        type(By.id("repeatedPassword"), u.confirm());
    }

    public void submit() {
        click(By.cssSelector("input[value='Register']"));
    }
}
