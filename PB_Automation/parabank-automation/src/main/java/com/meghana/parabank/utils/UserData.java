package com.meghana.parabank.utils;

/** Details used to register a customer. The username is unique on every run. */
public record UserData(String firstName, String lastName, String address, String city, String state,
                       String zip, String phone, String ssn, String username, String password, String confirm) {

    public static UserData withUsername(String username, String password, String confirm) {
        return new UserData("Test", "User", "12 MG Road", "Bengaluru", "Karnataka", "560001",
                "9876543210", "111-22-3333", username, password, confirm);
    }

    public static UserData newCustomer() {
        return withUsername("tester_" + System.currentTimeMillis(), "Test@123", "Test@123");
    }
}
