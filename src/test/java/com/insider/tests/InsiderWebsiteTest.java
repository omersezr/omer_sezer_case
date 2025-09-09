package com.insider.tests;

import com.insider.pages.*;
import com.insider.utils.DriverManager;
import org.testng.Assert;
import org.testng.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsiderWebsiteTest {

    private static final Logger logger = LoggerFactory.getLogger(InsiderWebsiteTest.class);
    private static final Logger testResultsLogger = LoggerFactory.getLogger("TEST_RESULTS");
    
    private HomePage homePage;
    private CareersPage careersPage;
    private QACareersPage qaCareersPage;
    private LeverApplicationPage leverApplicationPage;

    @BeforeMethod
    public void setUp() {
        logger.info("=== TEST SETUP STARTED ===");
        try {
            DriverManager.initializeDriver("chrome");
            initializePages();
            logger.info("✓ Test environment setup completed successfully");
        } catch (Exception e) {
            logger.error("✗ Test setup failed: {}", e.getMessage(), e);
            throw e;
        }
        logger.info("=== TEST SETUP COMPLETED ===");
    }

    @AfterMethod
    public void tearDown() {
        logger.info("=== TEST TEARDOWN STARTED ===");
        try {
            DriverManager.quitDriver();
            logger.info("✓ Test environment cleanup completed successfully");
        } catch (Exception e) {
            logger.error("✗ Test teardown failed: {}", e.getMessage(), e);
        }
        logger.info("=== TEST TEARDOWN COMPLETED ===");
    }

    private void initializePages() {
        logger.debug("Initializing page objects");
        homePage = new HomePage(DriverManager.getDriver());
        careersPage = new CareersPage(DriverManager.getDriver());
        qaCareersPage = new QACareersPage(DriverManager.getDriver());
        leverApplicationPage = new LeverApplicationPage(DriverManager.getDriver());
        logger.debug("All page objects initialized successfully");
    }
    
    private void logTestStart(String testName, String description) {
        logger.info("╔══════════════════════════════════════════════════════════════════════════════════════════╗");
        logger.info("║ STARTING TEST: {}", testName);
        logger.info("║ DESCRIPTION: {}", description);
        logger.info("╚══════════════════════════════════════════════════════════════════════════════════════════╝");
        testResultsLogger.info("TEST STARTED: {} - {}", testName, description);
    }
    
    private void logTestSuccess(String testName) {
        logger.info("╔══════════════════════════════════════════════════════════════════════════════════════════╗");
        logger.info("║ ✓ TEST PASSED: {}", testName);
        logger.info("╚══════════════════════════════════════════════════════════════════════════════════════════╝");
        testResultsLogger.info("✓ TEST PASSED: {}", testName);
    }
    
    private void logTestFailure(String testName, Throwable e) {
        logger.error("╔══════════════════════════════════════════════════════════════════════════════════════════╗");
        logger.error("║ ✗ TEST FAILED: {}", testName);
        logger.error("║ ERROR: {}", e.getMessage());
        logger.error("╚══════════════════════════════════════════════════════════════════════════════════════════╝");
        testResultsLogger.error("✗ TEST FAILED: {} - ERROR: {}", testName, e.getMessage());
    }

    @Test(priority = 1, description = "Verify Insider home page loads correctly")
    public void testHomePageLoad() {
        String testName = "Home Page Load Test";
        logTestStart(testName, "Verify Insider home page loads correctly");
        
        try {
            homePage.navigateToHomePage();
            
            boolean isLoaded = homePage.isHomePageLoaded();
            Assert.assertTrue(isLoaded, "Home page should load correctly with Insider in title");
            
            logTestSuccess(testName);
        } catch (Exception e) {
            logTestFailure(testName, e);
            throw e;
        }
    }

    @Test(priority = 2, description = "Verify Careers page and its blocks")
    public void testCareersPageAndBlocks() {
        String testName = "Careers Page and Blocks Test";
        logTestStart(testName, "Verify Careers page navigation and required blocks presence");
        
        try {
            homePage.navigateToHomePage();
            careersPage = homePage.navigateToCareers();
            
            logger.info("Verifying careers page is loaded correctly");
            boolean isPageLoaded = careersPage.isCareersPageLoaded();
            Assert.assertTrue(isPageLoaded, "Careers page should load correctly");
            logger.info("✓ Careers page loaded successfully");
            
            logger.info("Checking presence of required blocks on careers page");
            
            boolean locationsPresent = careersPage.isLocationsBlockPresent();
            Assert.assertTrue(locationsPresent, "Locations block should be present");
            logger.info("✓ Locations block is present");
            
            boolean teamsPresent = careersPage.isTeamsBlockPresent();
            Assert.assertTrue(teamsPresent, "Teams block should be present");
            logger.info("✓ Teams block is present");
            
            boolean lifeAtInsiderPresent = careersPage.isLifeAtInsiderBlockPresent();
            Assert.assertTrue(lifeAtInsiderPresent, "Life at Insider block should be present");
            logger.info("✓ Life at Insider block is present");
            
            boolean allBlocksPresent = careersPage.areAllBlocksPresent();
            Assert.assertTrue(allBlocksPresent, "All blocks (Locations, Teams, Life at Insider) should be present on careers page");
            logger.info("✓ All required blocks verified successfully");
            
            logTestSuccess(testName);
        } catch (Exception e) {
            logTestFailure(testName, e);
            throw e;
        }
    }

    @Test(priority = 3, description = "Verify QA jobs filtering functionality")
    public void testQAJobsFiltering() {
        qaCareersPage = new QACareersPage(DriverManager.getDriver());
        DriverManager.getDriver().get("https://useinsider.com/careers/quality-assurance/");
        
        qaCareersPage.clickSeeAllQAJobs();
        qaCareersPage.filterByLocation("Istanbul, Turkiye");
        qaCareersPage.filterByDepartment("Quality Assurance");
        
        Assert.assertTrue(qaCareersPage.isJobsListPresent(), 
            "Jobs list should be present after filtering");
    }

    @Test(priority = 4, description = "Verify job details meet filtering criteria")
    public void testJobDetailsValidation() {
        qaCareersPage = new QACareersPage(DriverManager.getDriver());
        DriverManager.getDriver().get("https://useinsider.com/careers/quality-assurance/");
        qaCareersPage.clickSeeAllQAJobs();
        qaCareersPage.filterByLocation("Istanbul, Turkiye");
        qaCareersPage.filterByDepartment("Quality Assurance");
        
        Assert.assertTrue(qaCareersPage.isJobsListPresent(), 
            "Jobs list should be present for validation");
        
        Assert.assertTrue(qaCareersPage.allPositionsContainQA(), 
            "All job positions should contain 'Quality Assurance' or 'QA'");
        
        Assert.assertTrue(qaCareersPage.allDepartmentsContainQA(), 
            "All job departments should contain 'Quality Assurance'");
        
        Assert.assertTrue(qaCareersPage.allLocationsContainIstanbul(), 
            "All job locations should contain 'Istanbul, Turkiye'");
    }

    @Test(priority = 5, description = "Verify View Role button redirects to Lever Application form")
    public void testViewRoleRedirection() {
        qaCareersPage = new QACareersPage(DriverManager.getDriver());
        DriverManager.getDriver().get("https://useinsider.com/careers/quality-assurance/");
        qaCareersPage.clickSeeAllQAJobs();
        qaCareersPage.filterByLocation("Istanbul, Turkiye");
        qaCareersPage.filterByDepartment("Quality Assurance");
        
        Assert.assertTrue(qaCareersPage.isJobsListPresent(), 
            "Jobs list should be present to click View Role");
        
        leverApplicationPage = qaCareersPage.clickFirstViewRoleButton();
        
        Assert.assertTrue(leverApplicationPage.isLeverApplicationPage(), 
            "Should redirect to Lever application form page");
    }

    @Test(priority = 6, description = "Complete end-to-end test scenario")
    public void testCompleteScenario() {
        homePage.navigateToHomePage();
        Assert.assertTrue(homePage.isHomePageLoaded(), 
            "Home page should load correctly");
        
        careersPage = homePage.navigateToCareers();
        Assert.assertTrue(careersPage.isCareersPageLoaded(), 
            "Careers page should load correctly");
        Assert.assertTrue(careersPage.areAllBlocksPresent(), 
            "All required blocks should be present");
        
        qaCareersPage = careersPage.navigateToQACareers();
        qaCareersPage.clickSeeAllQAJobs();
        qaCareersPage.filterByLocation("Istanbul, Turkiye");
        qaCareersPage.filterByDepartment("Quality Assurance");
        Assert.assertTrue(qaCareersPage.isJobsListPresent(), 
            "Filtered jobs list should be present");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        Assert.assertTrue(qaCareersPage.allPositionsContainQA(), 
            "All positions should contain QA");
        Assert.assertTrue(qaCareersPage.allDepartmentsContainQA(), 
            "All departments should contain Quality Assurance");
        Assert.assertTrue(qaCareersPage.allLocationsContainIstanbul(), 
            "All locations should contain Istanbul, Turkiye");
        
        leverApplicationPage = qaCareersPage.clickFirstViewRoleButton();
        Assert.assertTrue(leverApplicationPage.isLeverApplicationPage(), 
            "Should redirect to Lever application form");
    }
}
