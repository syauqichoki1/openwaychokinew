package com.gmailtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

public class GmailTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private final String EMAIL = "syauqiopenway@gmail.com";
    private final String PASSWORD = "syauqiopenway123"; // Replace with actual Google App Password

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
    }

    @Test
    public void loginAndFetchUnreadEmails() {
        // Open Gmail Login Page
        driver.get("https://mail.google.com/");

        // Enter Email
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId")));
        emailField.sendKeys(EMAIL);
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Next']")));
        nextButton.click();

        // Enter Password
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd")));
        passwordField.sendKeys(PASSWORD);
        nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Next']")));
        nextButton.click();

        // Wait for Inbox to Load
        wait.until(ExpectedConditions.titleContains("Inbox"));

        // Fetch All Unread Emails
        List<WebElement> unreadEmails = driver.findElements(By.cssSelector("tr.zA.zE"));

        if (unreadEmails.isEmpty()) {
            System.out.println("No unread emails found.");
        } else {
            // Fetch Earliest Unread Email (First)
            WebElement firstUnreadEmail = unreadEmails.get(0);
            String firstEmailTitle = firstUnreadEmail.findElement(By.cssSelector("span.bog")).getText();

            // etch Latest Unread Email (Last)
            WebElement lastUnreadEmail = unreadEmails.get(unreadEmails.size() - 1);
            String lastEmailTitle = lastUnreadEmail.findElement(By.cssSelector("span.bog")).getText();

            // Outpt
            System.out.println("\n**Unread Emails Found:**");
            System.out.println("Earliest Unread Email: " + firstEmailTitle);
            System.out.println("Latest Unread Email: " + lastEmailTitle);
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
