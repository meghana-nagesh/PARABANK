package com.meghana.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class UpdateProfilePage extends BasePage {
    private final By address = By.id("customer.address.street");
    private final By city = By.id("customer.address.city");
    private final By phone = By.id("customer.phoneNumber");

    public UpdateProfilePage(WebDriver driver) {
        super(driver);
    }

    public UpdateProfilePage open() {
        clickMenu("Update Contact Info");
        visible(address);
        // ParaBank fills this form (name, address, state, zip...) a moment AFTER it appears.
        // If we type before that, the other fields stay empty and the site says 'First name is required'.
        try {
            quick.until(d -> !d.findElement(By.id("customer.firstName")).getAttribute("value").isEmpty());
        } catch (TimeoutException e) {
            throw new AssertionError("The Update Contact Info form did not load the customer's saved details. "
                    + debugInfo());
        }
        return this;
    }

    public void update(String newAddress, String newCity, String newPhone) {
        type(address, newAddress);
        type(city, newCity);
        type(phone, newPhone);
        click(By.cssSelector("input[value='Update Profile']"));
    }

    // ParaBank fills this form a moment after the page opens, so wait until the field has a value.
    private String savedValue(By field) {
        try {
            quick.until(d -> !d.findElement(field).getAttribute("value").isEmpty());
        } catch (TimeoutException e) {
            // still empty: return it, so the test shows the real (empty) value
        }
        return visible(field).getAttribute("value");
    }

    public String savedAddress() { return savedValue(address); }
    public String savedCity()    { return savedValue(city); }
    public String savedPhone()   { return savedValue(phone); }
}