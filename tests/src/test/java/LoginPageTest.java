import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageTest extends TestBase {

    @Test
    public void testLogin() {
        doLogin();
        try {
            WebElement header = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.month-control-container.short-month-format")
                )
            );
            Assert.assertNotNull("Login failed: header not visible.", header);
            System.out.println("----- Login test passed. -----");
        } catch (TimeoutException e) {
            Assert.fail("Login failed: header not found after waiting.");
        }
    }

    @Test
    public void testLogout() throws Exception {
        doLogin();
        WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(
             By.xpath(" /html/body/app-root/div/div[1]/app-menu/div/app-profile-toggle-menu")));
        menuButton.click();
        Thread.sleep(1000);

        System.out.println("User menu loaded.");

        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/div/div[1]/app-menu/div/app-profile-toggle-menu/app-toggle-menu/div/div[2]/p-menu/div/ul/li[7]/a")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutLink);
        System.out.println("Logout link clicked by JavaScript.");
        Thread.sleep(1000);

       try {
            WebDriverWait longWait = new WebDriverWait(driver, 20);
            WebElement emailField = longWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_input")));
            boolean isLoginVisible = emailField.isDisplayed();
            System.out.println("Login is located. isLoginVisible=" + isLoginVisible);
            assertTrue("Logout failed: login page not displayed.", isLoginVisible);
        } catch (TimeoutException e) {
            System.out.println("Login field not visible after logout.");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            throw e;
        }
    
        System.out.println("----- Logout test passed. -----");
    }

}
