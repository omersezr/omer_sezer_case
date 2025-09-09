package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage extends BasePage {

    private final By locationsBlock = By.xpath("//h3[contains(text(), 'Our Locations')]");
    private final By teamsBlock = By.xpath("//h3[contains(text(), 'Find your calling')]");
    private final By lifeAtInsiderBlock = By.xpath("//h2[contains(text(), 'Life at Insider')]");
    
    private final By locationsContent = By.cssSelector("ul[class='glide__slides']");
    private final By teamsContent = By.cssSelector("div[class='job-image text-center']");
    private final By lifeAtInsiderContent = By.cssSelector("div[class='swiper-wrapper']");

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCareersPageLoaded() {
        try {
            return getCurrentUrl().contains("/careers/") && 
                   getPageTitle().toLowerCase().contains("careers");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLocationsBlockPresent() {
        return isElementPresent(locationsBlock) && isElementPresent(locationsContent);
    }

    public boolean isTeamsBlockPresent() {
        return isElementPresent(teamsBlock) && isElementPresent(teamsContent);
    }

    public boolean isLifeAtInsiderBlockPresent() {
        return isElementPresent(lifeAtInsiderBlock) && isElementPresent(lifeAtInsiderContent);
    }

    public QACareersPage navigateToQACareers() {
        driver.get("https://useinsider.com/careers/quality-assurance/");
        return new QACareersPage(driver);
    }

    public boolean areAllBlocksPresent() {
        return isLocationsBlockPresent() && 
               isTeamsBlockPresent() && 
               isLifeAtInsiderBlockPresent();
    }
    
    public boolean isLocationsContentLoaded() {
        try {
            return driver.findElements(locationsContent).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isTeamsContentLoaded() {
        try {
            return driver.findElements(teamsContent).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isLifeAtInsiderContentLoaded() {
        try {
            return driver.findElements(lifeAtInsiderContent).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
