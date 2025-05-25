import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    protected void doLogin() {
        System.out.println("Navigating to the login page...");
        driver.get("https://app.wieldy.hu/hu/login");

        handleCookieConsent();

        try {
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_input")));
            emailField.sendKeys("liszkai.dorka@agilexpert.hu");
            System.out.println("Username entered.");

            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password_input")));
            passwordField.sendKeys("Kiskutya22.");
            System.out.println("Password entered.");

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='button']")));

            try {
                loginButton.click();
                System.out.println("Login button clicked.");
            } catch (Exception e) {
                System.out.println("Standard click failed, attempting JavaScript click...");
                ((RemoteWebDriver) driver).executeScript("arguments[0].click();", loginButton);
                System.out.println("Login button clicked via JavaScript.");
            }

            System.out.println("Login successful.");

        } catch (Exception e) {
            System.out.println("An error occurred during the login process: " + e.getMessage());
            throw e;
        }
    }

    private void handleCookieConsent() {
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
            cookieButton.click();
            System.out.println("Cookie consent accepted.");
        } catch (TimeoutException e) {
            System.out.println("No cookie consent popup found.");
        }
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
