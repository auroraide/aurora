package aurora.client.view.editor;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Before
    public void setUp() {
        driver.navigate().to("http://localhost:4000/");
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

    private WebElement getStepButton() {
        return driver.findElement(By.id("stepButton"));
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
     * T6.1, T6.2
     */
    @Test
    public void addUserLibFunctionAndUse() {
        waitForElemExists(By.id("addFunctionButton")).click();
        waitForElemExists(By.id("nameField")).sendKeys("plus");
        waitForElemExists(By.id("functionField")).sendKeys("(\\m. \\n. \\s. \\z. m s (n s z))");
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
        waitForElemExists(By.id("addFunctionButton")).click();
        waitForElemExists(By.id("nameField")).sendKeys("first2");
        waitForElemExists(By.id("functionField")).sendKeys("λp.p(λa.λb.a)");
        waitForElemExists(By.id("addButton")).click();

        getInput().sendKeys("$first2 ($pair a b)");
        getRunButton().click();

        assertEquals("a", getOutput());
    }

    /**
     * T6.4
     */
    @Test
    public void removeFirst() {
        waitForElemExists(By.id("addFunctionButton")).click();
        waitForElemExists(By.id("nameField")).sendKeys("first2");
        waitForElemExists(By.id("functionField")).sendKeys("λp.p(λa.λb.a)");
        waitForElemExists(By.id("addButton")).click();

        getInput().sendKeys("$first2 ($pair a b)");
        getRunButton().click();

        driver.findElement((By.id("removeLibraryItemButton-0"))).click();

        assertTrue(driver.findElements(By.id("removeLibraryItemButton-0")).size() == 0);
    }

    /**
     * T6.5 T6.6
     */
    @Test
    public void testUseStdlibFunctionAddTwoTwo() {
        getInput().sendKeys("$add 2 2");
        getRunButton().click();
        assertEquals("4", getOutput());
    }




}
