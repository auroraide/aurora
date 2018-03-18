package aurora.client.view.popup;

import aurora.utils.SeleniumTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class AddLibraryItemDialogBoxSeleniumTest extends SeleniumTemplate {

    @Before
    public void navigateToPage() {
        driver.navigate().to("http://localhost:4000");
    }

    @Test
    public void testBackSlashToLambda() {
        driver.findElement(By.id("addFunctionButton")).click();
        driver.findElement(By.id("functionField")).click();
        driver.findElement(By.id("functionField")).sendKeys("(\\x. x x) (\\x. x x)");

        Assert.assertEquals("(λx. x x) (λx. x x)", driver.findElement(By.id("functionField"))
                .getAttribute("value"));
    }

}
