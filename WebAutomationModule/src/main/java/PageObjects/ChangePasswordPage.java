package PageObjects;

import General.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChangePasswordPage {
    String oldPassword = "!Qualis1!";
    private WebDriver driver;
    private LandingPage landingPage;

    @FindBy(id = "currentPassword")
    WebElement currentPassword;
    @FindBy(id = "newPassword")
    WebElement newPassword;
    @FindBy(id = "repeatedPassword")
    WebElement confirmPassword;
    @FindBy(id = "saveUser")
    WebElement saveNewPassword;

    public ChangePasswordPage(WebDriver driver) {
        this.driver = driver;
        landingPage = new LandingPage(driver);
        PageFactory.initElements(driver, this);
    }

    public LandingPage getLandingPage() {
        return landingPage;
    }

    public void EnterOldPassword(){
        landingPage.waitForElementToAppear(currentPassword);
        currentPassword.clear();
        currentPassword.sendKeys(oldPassword);
    }
}