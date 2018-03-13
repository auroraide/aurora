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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class EditorSeleniumIntegrationTest {
    private final String pathToEditor = "//*[@id=\"inputCodeMirror\"]/div/div[1]/textarea";
    private static WebDriver driver;
    private WebElement codeEditor;

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

    /**
     * Tests T4.1 (See Pflichtenheft).
     */
    @Test
    public void testIrreducibleLambdaTerm() {
        codeEditor.sendKeys("λx.x");
        driver.findElement(By.id("runButton")).click();
        String result = driver.findElement(By.id("outputCodeMirror")).findElements(By.tagName("pre")).get(0).getText();
        assertEquals("λx. x", result);
    }

    /**
     * Tests T4.2 (See Pflichtenheft).
     */
    @Test
    public void testOneBetaReduction() {
        codeEditor.sendKeys("(λx.x) z");
        driver.findElement(By.id("runButton")).click();
        String result = driver.findElement(By.id("outputCodeMirror")).findElements(By.tagName("pre")).get(0).getText();
        assertEquals("z", result);
    }

    /**
     * Tests T4.3 (See Pflichtenheft).
     */
    @Test
    public void testPlusTwoTwoReduction() {
        codeEditor.sendKeys("(λn.λm.λs.λz.n s (m s z)) (λs.λz.s(s z)) (λs.λz.s(s z))");
        driver.findElement(By.id("runButton")).click();
        String result = driver.findElement(By.id("outputCodeMirror")).findElements(By.tagName("pre")).get(0).getText();
        assertEquals("4", result);
    }

    /**
     * Tests T4.5 (See Pflichtenheft).
     */
    @Test
    public void testNoResultOnResetBtnClicked() {
        codeEditor.sendKeys("$pow 5 5");
        driver.findElement(By.id("runButton")).click();
        int result = driver.findElement(By.id("outputCodeMirror")).findElements(By.tagName("pre"))
                .get(0).getText().hashCode();
        assertEquals(8203, result);
    }

    /**
     * Tests T4.6 (See Pflichtenheft.)
     */
    @Test
    public void testEditingInEditorNotPossible() {
        codeEditor.sendKeys("(λ.x x) (λx.x x)");
        driver.findElement(By.id("runButton")).click();
        codeEditor.click();
        assertFalse("Is code editor selected", codeEditor.isSelected());
    }

    /**
     * Tests T4.7 (See Pflichtenheft).
     */
    @Test
    public void testContinueButtonClickedPossible() {
        codeEditor.sendKeys("$pow 5 5");
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
        codeEditor.sendKeys("(");
        // document.getElementById("inputCodeMirror").getElementsByTagName("pre")[0].textContent
        String editorContent = "" + driver.findElement(By.id("inputCodeMirror")).findElements(By.tagName("pre"))
                .get(1).getText();
        assertEquals("()", editorContent);
    }

    /**
     * Tests T4.10 (See Pflichtenheft).
     */
    @Test
    public void testResultFieldNotEditable() {
        WebElement resultField = driver.findElement(By.id("outputCodeMirror"))
                .findElements(By.tagName("pre")).get(0);
        resultField.click();
        assertFalse("Result field should not be clickable", resultField.isSelected());
    }

    /**
     * Tests T4.11 (See Pflichtenheft).
     */
    @Test
    public void testCommentsIgnoredInCalculation() {
        codeEditor.sendKeys("#dies ist ein test (λz.z) a");
        codeEditor.sendKeys(Keys.RETURN);
        System.out.println(driver.findElement(By.id("inputCodeMirror")).
                findElements(By.tagName("pre")).get(0).getText());
        codeEditor.sendKeys("(λ x. x) y");
        driver.findElement(By.id("runButton")).click();
        String result = driver.findElement(By.id("outputCodeMirror")).findElements(By.tagName("pre")).get(0).getText();
        assertEquals("z", result);
    }
}
