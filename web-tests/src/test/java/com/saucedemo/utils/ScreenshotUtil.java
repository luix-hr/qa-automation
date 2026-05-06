package com.saucedemo.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    private static final String DIR = "target/screenshots";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public static void capture(WebDriver driver, String testName) {
        try {
            Path dir = Paths.get(DIR);
            if (!Files.exists(dir)) Files.createDirectories(dir);

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String safe = testName.replaceAll("[^a-zA-Z0-9-_]", "_");
            String filename = safe + "_" + LocalDateTime.now().format(FMT) + ".png";
            Files.copy(src.toPath(), dir.resolve(filename));
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
