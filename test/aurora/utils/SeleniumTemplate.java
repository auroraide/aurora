package aurora.utils;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumTemplate {
    protected final String pathToEditor = "//*[@id=\"inputCodeMirror\"]/div/div[1]/textarea";
    protected static WebDriver driver;
    protected WebElement codeEditor;

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

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        driver.navigate().to("http://localhost:4000");
        codeEditor = driver.findElement(By.xpath(pathToEditor));
    }


}
