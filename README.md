# QA Automation Test Project

## 🚀 Project Overview

This is a comprehensive Selenium WebDriver automation testing project built with Java and TestNG. The project demonstrates end-to-end testing capabilities for a corporate website, including navigation, form interactions, and data validation.

## 🛠 Tech Stack

- **Language**: Java 21
- **Testing Framework**: TestNG 7.8.0
- **Web Automation**: Selenium WebDriver 4.15.0
- **Build Tool**: Maven
- **Logging**: SLF4J + Logback
- **Design Pattern**: Page Object Model (POM)

## 📁 Project Structure

```
src/
├── main/java/com/
│   ├── pages/
│   │   ├── BasePage.java
│   │   ├── HomePage.java
│   │   ├── CareersPage.java
│   │   ├── QACareersPage.java
│   │   └── LeverApplicationPage.java
│   └── utils/
│       └── DriverManager.java
└── test/java/com/
    └── tests/
        └── WebsiteTest.java
```

## 🎯 Test Scenarios

The automation suite covers the following test cases:

1. **Home Page Verification**
   - Navigate to home page
   - Verify page loads correctly
   - Handle cookies acceptance

2. **Careers Navigation**
   - Navigate through Company → Careers menu
   - Verify careers page elements
   - Validate required content blocks (Locations, Teams, Life at Company)

3. **QA Jobs Filtering**
   - Access QA careers section
   - Apply location filter (Istanbul, Turkey)
   - Apply department filter (Quality Assurance)
   - Verify filtered results

4. **Job Listings Validation**
   - Validate job position titles contain "Quality Assurance"
   - Verify department information
   - Confirm location details

5. **Application Form Redirection**
   - Click "View Role" button
   - Verify redirection to Lever application form
   - Validate application page elements

## 🔧 Prerequisites

- Java JDK 21 or higher
- Maven 3.6+
- Google Chrome browser
- ChromeDriver (included in `/web_driver/` directory)

## 📦 Installation & Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd omer_sezer_case
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Verify ChromeDriver**
   - ChromeDriver is included in `web_driver/chromedriver`
   - Ensure it has execute permissions:
   ```bash
   chmod +x web_driver/chromedriver
   ```

## 🚀 Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test
```bash
mvn test -Dtest=WebsiteTest#testHomePageLoad
```

### Run with TestNG XML
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

## 📊 Test Reports

After execution, reports are generated in:
- `target/surefire-reports/index.html` - TestNG HTML Report
- `target/surefire-reports/emailable-report.html` - Email-friendly Report

## 📝 Logging System

### Log Files Location
```
target/logs/
├── test-execution.log
├── test-results.log
├── error.log
└── debug.log
```

### Log Levels
- **INFO**: Test steps and general information
- **DEBUG**: Detailed element interactions and WebDriver actions
- **ERROR**: Failures, exceptions, and error details
- **WARN**: Warnings and fallback actions

### Sample Log Output
```
11:05:34.728 [main] INFO  c.tests.WebsiteTest - ╔═══════════════════════════════════════════╗
11:05:34.728 [main] INFO  c.tests.WebsiteTest - ║ STARTING TEST: Home Page Load Test
11:05:34.729 [main] INFO  c.tests.WebsiteTest - ║ DESCRIPTION: Verify home page loads correctly
11:05:34.730 [main] INFO  c.pages.BasePage - Test Step: Navigating to home page
11:05:38.622 [main] INFO  c.pages.BasePage - ✓ SUCCESS: Successfully navigated to home page
```

## 🏗 Architecture & Design Patterns

### Page Object Model (POM)
- **BasePage**: Contains common WebDriver operations and utilities
- **Page Classes**: Each page has its own class with specific locators and methods
- **Test Classes**: Focus on test logic without WebDriver implementation details

### Key Features
- **Thread-safe WebDriver management**
- **Explicit waits with custom timeout handling**
- **Comprehensive error handling and logging**
- **Reusable utility methods**
- **Clean separation of concerns**

## 🔍 Selectors Strategy

### CSS Selectors (Preferred)
```java
By.cssSelector("a.dropdown-sub[href*='careers']")
By.cssSelector("#jobs-list .position-list-item")
```

### XPath (When Necessary)
```java
By.xpath("//h3[contains(text(), 'Our Locations')]")
By.xpath("//a[contains(text(), 'Company') and @id='navbarDropdownMenuLink']")
```

## 🚦 Error Handling

The framework includes robust error handling:
- **TimeoutException**: Handled with descriptive error messages
- **ElementClickInterceptedException**: JavaScript fallback implementation
- **NoSuchElementException**: Graceful degradation with logging
- **StaleElementReferenceException**: Element re-location strategies

## 🔧 Configuration

### Browser Configuration
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-notifications");
options.addArguments("--disable-popup-blocking");
options.addArguments("--window-size=1920,1080");
```

### TestNG Configuration
```xml
<suite name="QATestSuite">
    <test name="WebsiteTests">
        <classes>
            <class name="com.tests.WebsiteTest"/>
        </classes>
    </test>
</suite>
```

## 📈 Best Practices Implemented

1. **Page Object Model**: Clean separation of page logic and test logic
2. **Explicit Waits**: No hard-coded Thread.sleep() in critical paths
3. **Assertions**: Meaningful assertion messages for better debugging
4. **Logging**: Comprehensive logging for test execution tracking
5. **Error Handling**: Graceful failure handling with fallback strategies
6. **Code Reusability**: Common functionality in BasePage class
7. **Configuration Management**: External configuration through XML and properties

## 🧪 Test Data & Environment

- **Base URL**: https://useinsider.com/
- **Test Environment**: Production website
- **Browser**: Google Chrome (latest stable version)
- **Screen Resolution**: 1920x1080
- **Timeout Values**: 10 seconds for element waits

## 📞 Troubleshooting

### Common Issues

1. **ChromeDriver Version Mismatch**
   - Update ChromeDriver in `web_driver/` directory
   - Match ChromeDriver version with installed Chrome browser

2. **Element Not Found**
   - Check debug.log for detailed element interaction logs
   - Verify locators in browser developer tools

3. **Test Timeouts**
   - Increase timeout values in BasePage constructor
   - Check network connectivity and website performance

4. **Permission Issues**
   - Ensure ChromeDriver has execute permissions
   - Run tests with appropriate user privileges

## 📊 Performance Considerations

- **Parallel Execution**: TestNG supports parallel test execution
- **Browser Cleanup**: Proper WebDriver cleanup in teardown methods
- **Memory Management**: ThreadLocal WebDriver instances
- **Wait Optimization**: Smart wait strategies to minimize test execution time

## 🔒 Security & Privacy

- **No Sensitive Data**: No credentials or sensitive information in code
- **Cookie Handling**: Automatic cookie acceptance for testing purposes
- **Data Privacy**: Tests do not submit or store personal information

---

*This automation framework demonstrates modern QA practices with robust error handling, comprehensive logging, and maintainable code architecture.*
