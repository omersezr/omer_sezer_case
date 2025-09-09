package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class QACareersPage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(QACareersPage.class);

    private final By seeAllQAJobsButton = By.cssSelector("a[href*='qualityassurance'].btn, a[class*='btn'][href*='quality']");
    private final By locationFilter = By.id("filter-by-location");
    private final By departmentFilter = By.id("filter-by-department");
    private final By jobsList = By.cssSelector("#jobs-list .position-list-item");
    private final By jobPositions = By.cssSelector(".position-list-item .position-title");
    private final By jobDepartments = By.cssSelector(".position-list-item .position-department");
    private final By jobLocations = By.cssSelector(".position-list-item .position-location");
    private final By viewRoleButtons = By.cssSelector(".position-list-item a.btn.btn-navy.rounded[target='_blank']");
    private final By acceptCookiesButton = By.id("wt-cli-accept-all-btn");
    private final By cookieBanner = By.id("cookie-law-info-bar");

    public QACareersPage(WebDriver driver) {
        super(driver);
    }

    public void acceptCookies() {
        try {
            if (isElementPresent(cookieBanner) && isElementPresent(acceptCookiesButton)) {
                clickElement(acceptCookiesButton);
            }
        } catch (Exception e) {
        }
    }

    public void clickSeeAllQAJobs() {
        logTestStep("Clicking 'See All QA Jobs' button");
        acceptCookies();
        
        try {
            clickElement(seeAllQAJobsButton);
            waitForElementToBeVisible(locationFilter);
            logSuccess("Successfully navigated to QA jobs page and filters are visible");
        } catch (Exception e) {
            logError("Failed to click 'See All QA Jobs' button or filters not visible", e);
            throw e;
        }
    }

    public void filterByLocation(String location) {
        logTestStep("Filtering jobs by location: " + location);
        
        try {
            waitForElementToBeClickable(locationFilter);
            WebElement locationDropdown = waitForElement(locationFilter);
            Select select = new Select(locationDropdown);
            
            logger.debug("Waiting for location dropdown options to load");
            try {
                wait.until(webDriver -> {
                    try {
                        List<WebElement> options = select.getOptions();
                        boolean hasEnoughOptions = options.size() > 5;
                        boolean hasIstanbul = options.stream()
                            .anyMatch(option -> option.getText().contains("Istanbul, Turkiye"));
                        return hasEnoughOptions && hasIstanbul;
                    } catch (Exception e) {
                        return false;
                    }
                });
                logger.debug("Location dropdown options loaded successfully");
            } catch (Exception e) {
                logger.warn("Location dropdown options loading timeout, proceeding anyway");
            }
            
            try {
                Thread.sleep(5000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            logger.info("Selecting location: {}", location);
            select.selectByVisibleText(location);
            waitForElementToBeVisible(jobsList);
            
            try {
                Thread.sleep(3000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            logSuccess("Successfully filtered jobs by location: " + location);
        } catch (Exception e) {
            logError("Failed to filter by location: " + location, e);
            throw e;
        }
    }

    public void filterByDepartment(String department) {
        waitForElementToBeClickable(departmentFilter);
        WebElement departmentDropdown = waitForElement(departmentFilter);
        Select select = new Select(departmentDropdown);
        select.selectByVisibleText(department);
        waitForElementToBeVisible(jobsList);
        
        try {
            Thread.sleep(7000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isJobsListPresent() {
        try {
            List<WebElement> jobs = driver.findElements(jobsList);
            return !jobs.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public List<WebElement> getJobPositions() {
        return driver.findElements(jobPositions);
    }

    public List<WebElement> getJobDepartments() {
        return driver.findElements(jobDepartments);
    }

    public List<WebElement> getJobLocations() {
        return driver.findElements(jobLocations);
    }


    public boolean allPositionsContainQA() {
        List<WebElement> positions = driver.findElements(jobPositions);
        if (positions.isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < positions.size(); i++) {
            List<WebElement> freshPositions = driver.findElements(jobPositions);
            if (i >= freshPositions.size()) {
                break;
            }
            
            String positionText = freshPositions.get(i).getText().toLowerCase();
            
            if (!positionText.contains("quality assurance") && !positionText.contains("qa") && !positionText.contains("software quality")) {
                return false;
            }
        }
        return true;
    }

    public boolean allDepartmentsContainQA() {
        List<WebElement> departments = driver.findElements(jobDepartments);
        if (departments.isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < departments.size(); i++) {
            List<WebElement> freshDepartments = driver.findElements(jobDepartments);
            if (i >= freshDepartments.size()) {
                break;
            }
            
            String departmentText = freshDepartments.get(i).getText().toLowerCase();
            if (!departmentText.contains("quality assurance")) {
                return false;
            }
        }
        return true;
    }

    public boolean allLocationsContainIstanbul() {
        List<WebElement> locations = driver.findElements(jobLocations);
        if (locations.isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < locations.size(); i++) {
            List<WebElement> freshLocations = driver.findElements(jobLocations);
            if (i >= freshLocations.size()) {
                break;
            }
            
            String locationText = freshLocations.get(i).getText().toLowerCase();
            
            boolean hasIstanbul = locationText.contains("istanbul") || locationText.contains("ıstanbul");
            boolean hasTurkiye = locationText.contains("turkiye");
            
            if (!hasIstanbul || !hasTurkiye) {
                return false;
            }
        }
        return true;
    }

    public LeverApplicationPage clickFirstViewRoleButton() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        List<WebElement> buttons = driver.findElements(viewRoleButtons);
        if (!buttons.isEmpty()) {
            WebElement firstButton = buttons.get(0);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", firstButton);
            
            String originalWindow = driver.getWindowHandle();
            wait.until(webDriver -> webDriver.getWindowHandles().size() > 1);
            
            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
        }
        return new LeverApplicationPage(driver);
    }
}
