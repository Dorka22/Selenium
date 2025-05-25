import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;


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

        try {
            System.out.println("Checking for cookie consent popup...");
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
            cookieButton.click();
            System.out.println("Cookie consent accepted.");
        } catch (TimeoutException e) {
            System.out.println("No cookie consent popup found.");
        }

        try {
            System.out.println("Locating username field...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_input"))).sendKeys("liszkai.dorka@agilexpert.hu");
            System.out.println("Username entered.");

            System.out.println("Locating password field...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password_input"))).sendKeys("Kiskutya22.");
            System.out.println("Password entered.");

            System.out.println("Locating login button...");
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='button']")));
            try {
                loginBtn.click();
                System.out.println("Login button clicked.");
            } catch (Exception clickException) {
                System.out.println("Standard click failed, attempting JavaScript click...");
                ((RemoteWebDriver) driver).executeScript("arguments[0].click();", loginBtn);
                System.out.println("Login button clicked via JavaScript.");
            }
            

            System.out.println("Login successful.");

        } catch (Exception e) {
            System.out.println("An error occurred during the login process: " + e.getMessage());
            throw e;
        }
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
