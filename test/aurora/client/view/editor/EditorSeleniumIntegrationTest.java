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

public class EditorSeleniumIntegrationTest {
    private final String pathToEditor = "//*[@id=\"inputCodeMirror\"]/div/div[1]/textarea";
    private static WebDriver driver;
//    private WebElement codeEditor;

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

    private WebElement getInput() {
        return driver.findElement(By.xpath(pathToEditor));
    }

    private WebElement getRunButton() {
        return driver.findElement(By.id("runButton"));
    }

    private String getOutput() {
//        return driver.findElement(By.id("outputCodeMirror"));
        return driver.findElement(By.id("outputCodeMirror")).findElements(By.tagName("pre")).get(1).getText();
    }

    private String waitForOutput() {
        Wait<WebDriver> waiter = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        return waiter.until(dr -> {
            String o = getOutput();
//            System.out.println("o = \"" + o + "\" (hashCode = " + o.hashCode() + ")");
            return o.isEmpty() ? null : o;
        });
    }

    @Test
    public void testRunning() {
        getInput().sendKeys("(\\x.x x a) (\\x. x x a)");
        getRunButton().click();

        // TODO continue
    }

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        driver.navigate().to("http://localhost:4000");
    }

    /**
     * Tests T4.1 (See Pflichtenheft).
     */
    @Test
    public void testIrreducibleLambdaTerm() {
        getInput().sendKeys("λx.x");
        driver.findElement(By.id("runButton")).click();
        assertEquals("λx. x", waitForOutput());
    }

    /**
     * Tests T4.2 (See Pflichtenheft).
     */
    @Test
    public void testOneBetaReduction() {
        getInput().sendKeys("(λx.x) z");
        driver.findElement(By.id("runButton")).click();
        assertEquals("z", waitForOutput());
    }

    /**
     * Tests T4.3 (See Pflichtenheft).
     */
    @Test
    public void testPlusTwoTwoReduction() {
        getInput().sendKeys("(λn.λm.λs.λz.n s (m s z)) (λs.λz.s(s z)) (λs.λz.s(s z))");
        driver.findElement(By.id("runButton")).click();
        assertEquals("4", waitForOutput());
    }

    /**
     * Tests T4.5 (See Pflichtenheft).
     */
    @Test
    public void testNoResultOnResetBtnClicked() {
        getInput().sendKeys("$pow 5 5");
        driver.findElement(By.id("runButton")).click();
        assertEquals(8203, getOutput().hashCode());
    }

    /**
     * Tests T4.6 (See Pflichtenheft.)
     */
    @Test
    public void testEditingInEditorNotPossible() {
        getInput().sendKeys("(λ.x x) (λx.x x)");
        driver.findElement(By.id("runButton")).click();
        getInput().click();
        assertFalse("Is code editor selected", getInput().isSelected());
    }

    /**
     * Tests T4.7 (See Pflichtenheft).
     */
    @Test
    public void testContinueButtonClickedPossible() {
        getInput().sendKeys("$pow 5 5");
        driver.findElement(By.id("runButton")).click();
        driver.findElement(By.id("pauseButton")).click();

        boolean continueBtnDisplayedAndEnabled = driver.findElement(By.id("continueButton")).isDisplayed()
                && driver.findElement(By.id("continueButton")).isEnabled();

        assertTrue("Continue button is displayed and visible", continueBtnDisplayedAndEnabled);
    }

    /**
     * Tests T4.8 (See Pflichtenheft).
     * TODO write tests when displaying error message dialogbox in editorview is implemented.
     */
    @Test
    public void testErrorMessageDisplayedByWrongInput() {

    }

    /**
     * Tests T4.9 (See Pflichtenheft).
     */
    @Test
    public void testBraceAutoComplete() {
        getInput().sendKeys("(");
        // document.getElementById("inputCodeMirror").getElementsByTagName("pre")[0].textContent
        String editorContent = "" + driver.findElement(By.id("inputCodeMirror")).findElements(By.tagName("pre"))
                .get(1).getText();
        assertEquals("()", editorContent);
    }

//    /**
//     * Tests T4.10 (See Pflichtenheft).
//     */
//    @Test
//    public void testResultFieldNotEditable() {
////        WebElement resultField = driver.findElement(By.id("outputCodeMirror"))
////                .findElements(By.tagName("pre")).get(0);
//        getOutput().click();
//        assertFalse("Result field should not be clickable", getOutput().isSelected());
//    }

    /**
     * Tests T4.11 (See Pflichtenheft).
     */
    @Test
    public void testCommentsIgnoredInCalculation() {
        getInput().sendKeys("(λ x. x) z");
        getInput().sendKeys(Keys.RETURN);
        getInput().sendKeys("#dies ist ein test (λz.z) a");
        getRunButton().click();

        assertEquals("z", waitForOutput());
    }
}
