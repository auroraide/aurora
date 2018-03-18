package aurora.client.view.editor;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class AddUserLibSeleniumTest {
    private static WebDriver driver;

    /**
     * Starts the webdriver.
     */
    @BeforeClass
    public static void startWebDriver() {
        driver = new JBrowserDriver(Settings.builder().headless(true).build());
    }

    @AfterClass
    public static void closeWebDriver() {
        driver.quit();
    }

    private Wait<WebDriver> waiter() {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
    }

    private String waitForTextNotEmpty(By by) {
        return waiter().until(dr -> {
            String o = dr.findElement(by).getText();
            return o.isEmpty() || o.hashCode() == 8203 ? null : o;
        });
    }

    private WebElement waitForElemExists(By by) {
        return waiter().until(dr -> dr.findElement(by));
    }

    private WebElement getInput() {
        String pathToEditor = "//*[@id=\"inputCodeMirror\"]/div/div[1]/textarea";
        return driver.findElement(By.xpath(pathToEditor));
    }

    private WebElement getRunButton() {
        return driver.findElement(By.id("runButton"));
    }

    private String getOutput() {
        return driver.findElement(By.id("outputCodeMirror")).findElements(By.tagName("pre")).get(1).getText();
    }

    private String waitForOutput() {
        Wait<WebDriver> waiter = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        return waiter.until(dr -> {
            String o = getOutput();
            return o.isEmpty() || o.hashCode() == 8203 ? null : o;
        });
    }

    /**
     * T6.2
     */
    @Test
    public void addUserLibFunctionAndUse() {
        waitForElemExists(By.id("addFunctionButton")).click();
        waitForElemExists(By.id("nameField")).sendKeys("plus");
        waitForElemExists(By.id("functionField")).sendKeys("\\p.p(\\a.\\b.a)");
        waitForElemExists(By.id("addButton")).click();

        getInput().sendKeys("$plus 5 8");
        getRunButton().click();

        assertEquals("13", getOutput());
    }

    /**
     * T6.3
     */
    @Test
    public void testFirstPair() {

    }
}
