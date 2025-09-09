package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ElementClickInterceptedException;

public class HomePage extends BasePage {

    private final By companyMenu = By.xpath("//a[contains(text(), 'Company') and @id='navbarDropdownMenuLink']");
    private final By careersLink = By.cssSelector("a.dropdown-sub[href*='careers']");
    private final By acceptCookiesButton = By.id("wt-cli-accept-all-btn");
    private final By cookieBanner = By.id("cookie-law-info-bar");
    private final By announcementBanner = By.id("announce");
    private final By announcementCloseButton = By.xpath("//div[@id='announce']//button[contains(@class, 'close') or contains(@class, 'btn-close') or @aria-label='Close']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToHomePage() {
        String url = "https://useinsider.com/";
        logTestStep("Navigating to Insider home page");
        logPageNavigation("Navigate", url);
        
        try {
            driver.get(url);
            logSuccess("Successfully navigated to home page");
            acceptCookies();
        } catch (Exception e) {
            logError("Failed to navigate to home page", e);
            throw e;
        }
    }

    public boolean isHomePageLoaded() {
        logTestStep("Verifying home page is loaded correctly");
        
        try {
            String title = getPageTitle();
            String currentUrl = getCurrentUrl();
            
            boolean isLoaded = title.toLowerCase().contains("insider") || currentUrl.contains("useinsider.com");
            
            if (isLoaded) {
                logSuccess("Home page loaded successfully - Title: '" + title + "' | URL: " + currentUrl);
            } else {
                logger.warn("Home page verification failed - Title: '{}' | URL: {}", title, currentUrl);
            }
            
            return isLoaded;
        } catch (Exception e) {
            logError("Error while verifying home page load", e);
            return false;
        }
    }

    public void acceptCookies() {
        logTestStep("Attempting to accept cookies if banner is present");
        
        try {
            if (isElementPresent(cookieBanner) && isElementPresent(acceptCookiesButton)) {
                logger.info("Cookie banner detected, attempting to accept cookies");
                clickElement(acceptCookiesButton);
                waitForElementToBeVisible(By.cssSelector("body"));
                logSuccess("Successfully accepted cookies");
            } else {
                logger.debug("No cookie banner found or accept button not available");
            }
        } catch (Exception e) {
            logger.warn("Failed to accept cookies, continuing anyway: {}", e.getMessage());
        }
    }
    
    public void dismissAnnouncementBanner() {
        logTestStep("Attempting to dismiss announcement banner if present");
        
        try {
            if (driver.findElements(announcementBanner).size() > 0) {
                WebElement banner = driver.findElement(announcementBanner);
                if (banner.isDisplayed()) {
                    logger.info("Announcement banner detected, attempting to dismiss");
                    
                    try {
                        if (driver.findElements(announcementCloseButton).size() > 0) {
                            clickElement(announcementCloseButton);
                            logSuccess("Dismissed announcement banner using close button");
                        } else {
                            JavascriptExecutor js = (JavascriptExecutor) driver;
                            js.executeScript("arguments[0].style.display = 'none';", banner);
                            logSuccess("Dismissed announcement banner using JavaScript");
                        }
                    } catch (Exception e) {
                        logger.warn("Failed to close banner with button, using JavaScript fallback");
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].style.display = 'none';", banner);
                        logSuccess("Dismissed announcement banner using JavaScript fallback");
                    }
                } else {
                    logger.debug("Announcement banner found but not displayed");
                }
            } else {
                logger.debug("No announcement banner found");
            }
        } catch (Exception e) {
            logger.warn("Error while dismissing announcement banner: {}", e.getMessage());
        }
    }

    public void clickCompanyMenu() {
        logTestStep("Clicking Company menu in navigation");
        dismissAnnouncementBanner();
        
        try {
            clickElement(companyMenu);
            logSuccess("Successfully clicked Company menu");
        } catch (ElementClickInterceptedException e) {
            logger.warn("Company menu click intercepted, using JavaScript fallback");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = waitForElement(companyMenu);
            js.executeScript("arguments[0].click();", element);
            logSuccess("Successfully clicked Company menu using JavaScript");
        } catch (Exception e) {
            logError("Failed to click Company menu", e);
            throw e;
        }
    }

    public CareersPage clickCareersLink() {
        logTestStep("Clicking Careers link from Company dropdown");
        
        try {
            clickElement(careersLink);
            logSuccess("Successfully clicked Careers link");
        } catch (ElementClickInterceptedException e) {
            logger.warn("Careers link click intercepted, using JavaScript fallback");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = waitForElement(careersLink);
            js.executeScript("arguments[0].click();", element);
            logSuccess("Successfully clicked Careers link using JavaScript");
        } catch (Exception e) {
            logError("Failed to click Careers link", e);
            throw e;
        }
        
        return new CareersPage(driver);
    }

    public CareersPage navigateToCareers() {
        logTestStep("Navigating to Careers page through Company menu");
        
        try {
            acceptCookies();
            dismissAnnouncementBanner();
            clickCompanyMenu();
            CareersPage careersPage = clickCareersLink();
            logSuccess("Successfully navigated to Careers page");
            return careersPage;
        } catch (Exception e) {
            logError("Failed to navigate to Careers page", e);
            throw e;
        }
    }
}
