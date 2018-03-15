package aurora.client.presenter;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EditorPresenterSeleniumTest {
    private static WebDriver webDriver;

    @BeforeClass
    public static void startWebDriver() {
        webDriver = new JBrowserDriver(Settings.builder().headless(true).build());
    }

    @AfterClass
    public static void closeWebDriver() {
        webDriver.quit();
    }

    @Test
    public void testSomething() {

    }
}
