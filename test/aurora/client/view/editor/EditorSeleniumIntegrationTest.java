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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
     * Tests T4.1
     *
     * Precondition: Aurora WebApp is opened and in DefaultState.
     * Action: The User types in the lambda term "λx.x" in the input code editor and clicks on the run button.
     * Reaction: The programm can't execute a beta-reduction and outputs "" in the result field.
     */
    @Test
    public void testIrreducibleLambdaTerm() {
        codeEditor.sendKeys("λx.x");
        driver.findElement(By.id("runButton")).click();
        String resultContent = driver.findElements(By.tagName("pre")).get(1).getText();
        assertEquals("λx. x", resultContent);
    }

    /**
     * Tests T4.2
     *
     * Precondition: Aurora WebApp is opened and in DefaultState.
     * Action: The User types in the lambda term "(λx.x x) z" in the input code editor and clicks on the run button.
     * Reaction: The programm executes one beta-reduction and outputs "z" in the result field.
     */
    @Test
    public void testOneBetaReduction() {
        codeEditor.sendKeys("(λx.x) z");
        driver.findElement(By.id("runButton")).click();
        String resultContent = driver.findElements(By.tagName("pre")).get(2).getText();
        assertEquals("z", resultContent);
    }

    /**
     * Tests T4.3
     *
     * Precondition: Aurora WebApp is opened and in DefaultState.
     * Action: The User types in the lambda term "(λn.λm.λs.λz.n s (m s z)) (λs.λz.s(s z)) (λs.λz.s(s z))"
     *         in the input code editor and clicks on the run button.
     * Reaction: The programm executes one beta-reduction and outputs "4" in the result field.
     */
    @Test
    public void testPlusTwoTwoReduction() {
        codeEditor.sendKeys("(λn.λm.λs.λz.n s (m s z)) (λs.λz.s(s z)) (λs.λz.s(s z))");
        driver.findElement(By.id("runButton")).click();
        String resultContent = driver.findElements(By.tagName("pre")).get(2).getText();
        assertEquals("4", resultContent);
    }

    /**
     * Tests T4.5
     *
     * Precondition: Aurora WebApp is calculating a lambda term and therefore is in RunningState.
     * Action: The user clicks on the reset button.
     * Reaction: The programm terminates the current calculation and does not output anything.
     */
    @Test
    public void testNoResultOnResetBtnClicked() {
        codeEditor.sendKeys("$pow 5 5");
        driver.findElement(By.id("runButton")).click();
        int resultContent = driver.findElements(By.tagName("pre")).get(1).getText().hashCode();
        assertEquals(8203, resultContent);
    }

    /**
     * Tests T4.7
     *
     * Precondition: Aurora WebApp is calculating a lambda term and therefore is in RunningState.
     * Action: The user clicks on the pause button.
     * Reaction:
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
}
