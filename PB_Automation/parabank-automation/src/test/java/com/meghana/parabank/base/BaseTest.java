package com.meghana.parabank.base;

import com.meghana.parabank.pages.HomePage;
import com.meghana.parabank.pages.OpenAccountPage;
import com.meghana.parabank.pages.OverviewPage;
import com.meghana.parabank.utils.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/** Every test class extends this. A fresh browser opens before each test and closes after it. */
public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        switch (Config.browser()) {
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                options.addPreference("signon.rememberSignons", false);
                if (Config.headless()) {
                    options.addArguments("-headless");
                }
                driver = new FirefoxDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                options.setExperimentalOption("prefs", passwordManagerOff());
                if (Config.headless()) {
                    options.addArguments("--headless=new");
                }
                driver = new EdgeDriver(options);
            }
            default -> {
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("prefs", passwordManagerOff());
                options.addArguments("--window-size=1920,1080", "--disable-notifications", "--disable-infobars");
                if (Config.headless()) {
                    options.addArguments("--headless=new");
                }
                driver = new ChromeDriver(options);
            }
        }
        driver.manage().window().maximize();
    }

    /** Switches off the browser password manager, so the 'Change your password' (data breach) pop-up never appears. */
    private static Map<String, Object> passwordManagerOff() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        return prefs;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    /** Logs in with the customer from config.properties and checks that Accounts Overview opens. */
    protected void loginAsCustomer() {
        HomePage home = new HomePage(driver).open();
        home.login(Config.username(), Config.password());
        OverviewPage overview = new OverviewPage(driver);
        Assert.assertTrue(overview.isLoaded(), "Login failed. Check parabank.username and parabank.password in "
                + "config.properties, or tick the 'verify you are human' box. " + overview.debugInfo());
    }

    /** A transfer needs two accounts. Opens a SAVINGS account if the customer has fewer than two. */
    protected void ensureTwoAccounts() {
        OverviewPage overview = new OverviewPage(driver).open();
        Assert.assertTrue(overview.isLoaded(), "Accounts Overview did not open. " + overview.debugInfo());
        int accounts = overview.accountCount();
        Assert.assertTrue(accounts >= 1, "This customer has no accounts or ParaBank showed an error, so the test cannot continue. "
                + overview.debugInfo());
        if (accounts < 2) {
            OpenAccountPage openAccount = new OpenAccountPage(driver).open();
            openAccount.openAccount("SAVINGS");
            Assert.assertTrue(openAccount.isOpened(), "Setup failed: second account not opened. " + openAccount.debugInfo());
        }
    }
}
