package aurora.client.view.sidebar;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static junit.framework.TestCase.assertEquals;

public class SidebarSeleniumTest {
    private static WebDriver driver;
    private WebElement stepNumberTextBox;

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
    public void navigateToPage() {
        driver.navigate().to("http://localhost:4000");
        stepNumberTextBox = driver.findElement(By.id("stepNumberTextBox"));
    }

    @Test
    public void testStepNumberBigInput() {
        stepNumberTextBox.sendKeys("5000"); // Equals typing in TextBox
        assertEquals("2048", stepNumberTextBox.getAttribute("value"));
    }

    @Test
    public void testStepNumberInvalidInput() {
        stepNumberTextBox.sendKeys("12木sdf");
        assertEquals("112", stepNumberTextBox.getAttribute("value"));
    }

    @Test
    public void testStepNumberEmpty() {
        stepNumberTextBox.clear();
        driver.findElement(By.id("stdlibLabel")).click();
        assertEquals("1", stepNumberTextBox.getAttribute("value"));
    }

}
