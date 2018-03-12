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
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

public class EditorSeleniumTest {
    private final String pathToEditor = "//*[@id=\"inputCodeMirror\"]/div/div[1]/textarea";
    private static WebDriver driver;
    private WebElement codeEditor;

    /**
     * Starts the webdriver.
     */
    @BeforeClass
    public static void startWebDriver() {
        driver = new JBrowserDriver(Settings.builder().headless(false).build());
    }

    @AfterClass
    public static void closeWebDriver() {
        driver.quit();
    }

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        driver.navigate().to("http://localhost:4000");
        codeEditor = driver.findElement(By.xpath(pathToEditor));
    }

    /**
     * Tests T4.1 as described in Pflichtenheft.
     */
    @Test
    public void testIrreducibleLambdaTerm() {
        codeEditor.sendKeys("λx.x x");
        driver.findElement(By.id("runButton")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String content = driver.findElements(By.tagName("pre")).get(1).getText();
        System.out.println(content);
        assertEquals("λx. x x", content);
    }
}
