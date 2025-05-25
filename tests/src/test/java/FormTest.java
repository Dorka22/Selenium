import org.junit.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FormTest extends TestBase {

    @Test
    public void formTest() throws Exception {
        doLogin();

        WebElement openPopupButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/div/div[2]/app-worktime-register-page/app-desktop-worktime-register-page/div/div/ax-calendar/div/div[1]/div[1]/div/button")));
        System.out.println("Button found: " + openPopupButton.getText());

        openPopupButton.click();
        Thread.sleep(1000);
        System.out.println("Clicked on 'Munka felvétele' button. Waiting for popup...");

        WebElement popup = driver.findElement(By.cssSelector(
            "app-popup-work-create-form ax-popup > div > div"
        ));

        WebElement dropdownLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("/html/body/app-root/div/div[2]/app-worktime-register-page/app-desktop-worktime-register-page/app-popup-work-create-form/ax-popup/div/div/div[2]/ax-form/form/div[1]/ax-dropdown-input[2]/ax-inline-field/ax-field/div/div/div[2]/div[1]/p-dropdown")
        ));
        String selectedValue = dropdownLabel.getText();
        System.out.println("Selected dropdown value: " + selectedValue);
        assertTrue("Dropdown should not be empty", !selectedValue.trim().isEmpty());

        WebElement input = driver.findElement(By.cssSelector("input[placeholder='Munka megnevezése']"));
        input.clear();
        input.sendKeys("teszt");
        System.out.println("Log name written into the field.");

        WebElement inputDescription = driver.findElement(By.cssSelector("input[placeholder='Munka leírása']"));
        inputDescription.clear();
        inputDescription.sendKeys("teszt leírás hosszan");
        System.out.println("Log description written into the field.");


        WebElement saveButton = driver.findElement(By.xpath(
            "/html/body/app-root/div/div[2]/app-worktime-register-page/"
        + "app-desktop-worktime-register-page/app-popup-work-create-form/"
        + "ax-popup/div/div/div[3]/button"
        ));
        saveButton.click();
        System.out.println("Clicked 'Mentés' button.");

        boolean isClickable;
        try {
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/div/div[2]/app-worktime-register-page/app-desktop-worktime-register-page/div/div/ax-calendar/div/div[1]/div[1]/div/button")));
        isClickable = true;
        } catch (TimeoutException e) {
        isClickable = false;
        }
        System.out.println("Work button clickable? " + isClickable);
        assertTrue("Work button clickable", isClickable);



        System.out.println("----- Form test passed. -----");
    }

    @Test
    public void hoverTest() throws Exception {
        doLogin();

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/div/div[2]/app-worktime-register-page/app-desktop-worktime-register-page/div/div/ax-calendar/div/div[1]/div[1]/div/button")));
        System.out.println("Button found: " + button.getText());

        String beforeHover = button.getCssValue("background-color");
        System.out.println("Before hover: " + beforeHover);

        Actions actions = new Actions(driver);
        actions.moveToElement(button).pause(Duration.ofMillis(500)).perform();

        Thread.sleep(500);

        String afterHover = button.getCssValue("background-color");
        System.out.println("After hover: " + afterHover);

        assertTrue("Background color should change on hover", !beforeHover.equals(afterHover));
        System.out.println("----- Hover test passed. -----");

    }

}
