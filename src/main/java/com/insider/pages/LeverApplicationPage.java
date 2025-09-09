package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LeverApplicationPage extends BasePage {

    private final By leverApplicationForm = By.xpath("//div[contains(@class, 'application')]");
    private final By jobTitle = By.xpath("//h2[contains(@class, 'posting-headline')]");
    private final By applyButton = By.xpath("//a[contains(@class, 'postings-btn') and contains(text(), 'Apply')]");

    public LeverApplicationPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLeverApplicationPage() {
        try {
            waitForElementToBeVisible(By.cssSelector("body"));
            
            String currentUrl = getCurrentUrl().toLowerCase();
            
            boolean isLeverPage = currentUrl.contains("lever") || 
                   currentUrl.contains("jobs.lever.co") ||
                   currentUrl.contains("apply") ||
                   isElementPresent(leverApplicationForm) ||
                   isElementPresent(applyButton);
            
            return isLeverPage;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isApplicationFormPresent() {
        return isElementPresent(leverApplicationForm) || isElementPresent(applyButton);
    }

    public String getJobTitle() {
        try {
            if (isElementPresent(jobTitle)) {
                return getElementText(jobTitle);
            }
            return getPageTitle();
        } catch (Exception e) {
            return "";
        }
    }
}
