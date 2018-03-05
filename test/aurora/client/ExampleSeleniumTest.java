package aurora.client;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static junit.framework.TestCase.assertTrue;

/**
 * An example test using selenium as instructed by this youtube video:
 * <a href="https://www.youtube.com/watch?v=nqaY0UgRcFQ">
 * "How to create a simple Selenium WebDriver test using Java and IntelliJ"</a>.
 */
public class ExampleSeleniumTest {

    @Test
    public void startWebDriver() {
        ChromeOptions options = new ChromeOptions();
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            System.setProperty("webdriver.chrome.driver", "../chromedriver.exe");
        } else if (os.contains("mac os x")) {
            System.setProperty("webdriver.chrome.driver", "../chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
            options.setBinary("/usr/bin/google-chrome-unstable");
        }

        options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to("http://localhost:4000");
        assertTrue("Aurora WebApp's title should be Aurora", driver.getTitle().equals("Aurora"));
    }

}
