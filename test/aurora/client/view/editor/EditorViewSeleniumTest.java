package aurora.client.view.editor;

import aurora.utils.SeleniumTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class EditorViewSeleniumTest extends SeleniumTemplate {
    private WebElement resultOption;
    private WebElement runButton;
    private WebElement codeEditor;
    private WebElement resultField;

    /**
     * Sets up testing environment.
     */
    @Before
    public void setUp() {
        driver.navigate().to("http://localhost:4000");
        resultOption = driver.findElement(By.id("resultFieldShareMenuBar-item0"));
        runButton = driver.findElement(By.id("runButton"));
        codeEditor = driver.findElement(By.xpath(pathToEditor));
        resultField = driver.findElement(By.id("outputCodeMirror"));
    }

    @Ignore
    @Test
    public void testExportLaTeXResultField() {
        codeEditor.sendKeys("1 1");
        runButton.click();
        Assert.assertEquals("1", resultField.findElements(By.tagName("pre")).get(0).getText());
        resultOption.click();
        driver.findElement(By.id("resultFieldShareMenuBar-item0-item0")).click();
        // document.getElementsByClassName("shareDialogBox")[0].getElementsByTagName("textarea")[0]
        String content = driver.findElements(By.className("shareDialogBox")).get(0)
                .findElements(By.tagName("textarea")).get(0).getAttribute("value");
        Assert.assertEquals("$\\lambda z.\\ \\lambda z1.\\ z\\ z1$", content);
    }

}
