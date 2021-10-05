package General;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    private static final int TIMEOUT = 10;
    private static final int POLLING = 100;

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEOUT, POLLING);
        PageFactory.initElements(driver, this);
    }

    public void waitForElementToAppear(WebElement locator){
        wait.until(ExpectedConditions.visibilityOf(locator));
    }

    public void waitForElementToDisappear(WebElement locator){
        wait.until(ExpectedConditions.invisibilityOf(locator));
    }

    public void waitForTextToDisappear(WebElement locator, String text){
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(locator,text)));
    }
}
