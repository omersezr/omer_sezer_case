package com.insider.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        logger.debug("Initialized BasePage with WebDriver: {}", driver.getClass().getSimpleName());
    }

    protected void clickElement(By locator) {
        try {
            logger.debug("Attempting to click element with locator: {}", locator);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Successfully clicked element: {}", locator);
        } catch (TimeoutException e) {
            logger.error("Failed to click element within timeout. Locator: {}", locator);
            throw new RuntimeException("Element not clickable within timeout: " + locator, e);
        } catch (Exception e) {
            logger.error("Unexpected error while clicking element: {}. Error: {}", locator, e.getMessage());
            throw e;
        }
    }

    protected String getElementText(By locator) {
        try {
            logger.debug("Getting text from element: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String text = element.getText();
            logger.debug("Retrieved text '{}' from element: {}", text, locator);
            return text;
        } catch (TimeoutException e) {
            logger.error("Failed to get text from element within timeout. Locator: {}", locator);
            throw new RuntimeException("Element not visible within timeout: " + locator, e);
        }
    }

    protected WebElement waitForElement(By locator) {
        try {
            logger.debug("Waiting for element to be visible: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.debug("Element is now visible: {}", locator);
            return element;
        } catch (TimeoutException e) {
            logger.error("Element not visible within timeout: {}", locator);
            throw e;
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            logger.debug("Checking if element is present: {}", locator);
            boolean isPresent = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
            logger.debug("Element present status for {}: {}", locator, isPresent);
            return isPresent;
        } catch (TimeoutException e) {
            logger.debug("Element not present (timeout): {}", locator);
            return false;
        } catch (NoSuchElementException e) {
            logger.debug("Element not found: {}", locator);
            return false;
        }
    }
    
    protected void waitForElementToBeVisible(By locator) {
        try {
            logger.debug("Waiting for element to be visible: {}", locator);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.debug("Element is now visible: {}", locator);
        } catch (TimeoutException e) {
            logger.error("Element not visible within timeout: {}", locator);
            throw new RuntimeException("Element not visible within timeout: " + locator, e);
        }
    }
    
    protected void waitForElementToBeClickable(By locator) {
        try {
            logger.debug("Waiting for element to be clickable: {}", locator);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            logger.debug("Element is now clickable: {}", locator);
        } catch (TimeoutException e) {
            logger.error("Element not clickable within timeout: {}", locator);
            throw new RuntimeException("Element not clickable within timeout: " + locator, e);
        }
    }

    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.debug("Current URL: {}", url);
        return url;
    }

    public String getPageTitle() {
        String title = driver.getTitle();
        logger.debug("Current page title: {}", title);
        return title;
    }
    
    protected void logPageNavigation(String action, String url) {
        logger.info("Page Navigation - Action: {} | URL: {}", action, url);
    }
    
    protected void logTestStep(String stepDescription) {
        logger.info("Test Step: {}", stepDescription);
    }
    
    protected void logSuccess(String operation) {
        logger.info("✓ SUCCESS: {}", operation);
    }
    
    protected void logError(String operation, Exception e) {
        logger.error("✗ ERROR: {} | Exception: {}", operation, e.getMessage(), e);
    }
}
