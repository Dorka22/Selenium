import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;

public class StaticPagesTest extends TestBase{
    @Test
    public void testStaticPage() throws Exception {
        doLogin();

        String staticPageUrl = "https://app.wieldy.hu/hu/worktime-register?year=2025&month=5";  
        driver.get(staticPageUrl);

        String expectedTitle = "Wieldy";
        wait.until(ExpectedConditions.titleIs(expectedTitle));
        assertEquals("Page title did not match!", expectedTitle, driver.getTitle());

        System.out.println("----- Static page test passed: " + staticPageUrl + " -----");

    }

}
