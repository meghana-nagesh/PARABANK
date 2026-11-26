package com.meghana.parabank.listeners;

import com.meghana.parabank.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/** Saves a screenshot of every failed test in target/screenshots. */
public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();
        if (instance instanceof BaseTest base && base.getDriver() != null) {
            try {
                File shot = ((TakesScreenshot) base.getDriver()).getScreenshotAs(OutputType.FILE);
                Path folder = Path.of("target", "screenshots");
                Files.createDirectories(folder);
                Path target = folder.resolve(result.getMethod().getMethodName() + "_" + System.currentTimeMillis() + ".png");
                Files.copy(shot.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                System.out.println("Could not save screenshot: " + e.getMessage());
            }
        }
    }
}
