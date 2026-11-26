package com.meghana.parabank.tests;

import com.meghana.parabank.base.BaseTest;
import com.meghana.parabank.pages.UpdateProfilePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProfileTests extends BaseTest {

    @Test(groups = "regression", description = "PB_TC_033 | Update address, city and phone number")
    public void PB_TC_033_updateContactInfo() {
        loginAsCustomer();
        UpdateProfilePage profile = new UpdateProfilePage(driver).open();
        profile.update("45 Residency Road", "Mysuru", "9123456780");

        Assert.assertTrue(profile.waitForText("Profile Updated"), "'Profile Updated' not shown. " + profile.debugInfo());
    }

    @Test(groups = "regression", description = "PB_TC_035 | Updated contact details are saved after logging in again")
    public void PB_TC_035_updatedDetailsSaved() {
        loginAsCustomer();
        UpdateProfilePage profile = new UpdateProfilePage(driver).open();
        profile.update("78 Lake View Road", "Hubballi", "9000012345");
        Assert.assertTrue(profile.waitForText("Profile Updated"), "'Profile Updated' not shown. " + profile.debugInfo());

        profile.logout();
        loginAsCustomer();
        UpdateProfilePage again = new UpdateProfilePage(driver).open();

        Assert.assertEquals(again.savedAddress(), "78 Lake View Road");
        Assert.assertEquals(again.savedCity(), "Hubballi");
        Assert.assertEquals(again.savedPhone(), "9000012345");
    }
}
