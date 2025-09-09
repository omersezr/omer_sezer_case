package com.insider.utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverManager {
    
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static void initializeDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                String chromeDriverPath = System.getProperty("user.dir") + "/web_driver/chromedriver";
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-web-security");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                driver.set(new ChromeDriver(chromeOptions));
                break;
                
            case "firefox":
                driver.set(new FirefoxDriver());
                break;
                
            case "edge":
                driver.set(new EdgeDriver());
                break;
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
        
        getDriver().manage().window().maximize();
    }
    
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
