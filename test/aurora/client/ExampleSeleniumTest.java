package aurora.client;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;

import static junit.framework.TestCase.assertTrue;

/**
 * An example test using selenium.
 *
 * "How to create a simple Selenium WebDriver test using Java and IntelliJ"</a>.
 */
public class ExampleSeleniumTest {

    @Test
    public void startWebDriver() {

        WebDriver driver = new JBrowserDriver(Settings.builder().headless(true).build());
        driver.navigate().to("http://localhost:4000");
        assertTrue("Aurora WebApp's title should be Aurora", driver.getTitle().equals("Aurora"));
    }

}

