package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UiTests extends BaseTest {

    @Test(groups = "regression", description = "PB_TC_051 | Home page shows the login panel and all main links")
    public void PB_TC_051_homePageUi() {
        HomePage home = new HomePage(driver).open();
        List<String> missing = home.missingElements();

        Assert.assertTrue(missing.isEmpty(), "Missing on the home page: " + missing);
        Assert.assertFalse(home.title().isBlank(), "Page title should not be empty");
    }
}
