package aurora.client;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static junit.framework.TestCase.assertTrue;

/**
 * An example test using selenium as instructed by this youtube video:
 * <a href="https://www.youtube.com/watch?v=nqaY0UgRcFQ">
 * "How to create a simple Selenium WebDriver test using Java and IntelliJ"</a>.
 */
public class ExampleSeleniumTest {

    @Test
    public void startWebDriver() {
        System.setProperty("webdriver.chrome.driver", "../chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://127.0.0.1:8888/Aurora.html");
        assertTrue("Aurora WebApp's title should be Aurora", driver.getTitle().equals("Aurora"));
    }

}

