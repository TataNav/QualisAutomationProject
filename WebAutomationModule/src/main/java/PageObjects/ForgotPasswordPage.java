package PageObjects;

import General.Helper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ForgotPasswordPage {
    private LandingPage landingPage;
    private WebDriver driver;

    private @FindBy(xpath = "//div[1]/mat-horizontal-stepper[1]/div[2]/div[1]/form[1]/div[1]/input[1]")
    WebElement email;
    private @FindBy(xpath = "//button[contains(text(),'Next')]")
    WebElement next;
    private @FindBy(xpath = "//ul/li/notifier-notification/p")
    WebElement notification;
    private @FindBy(xpath = "//*[@id='cdk-step-content-0-1']/form/input[1]")
    WebElement confirmationCode;
    private @FindBy(xpath = "//*[@id='cdk-step-content-0-1']/form/input[2]")
    WebElement newPassword;
    private @FindBy(xpath = "//*[@id='cdk-step-content-0-1']/form/input[3]")
    WebElement confirmNewPassword;
    private @FindBy(xpath = "//button[contains(text(),'Submit')]")
    WebElement submit;
    private @FindBy(xpath = "//input[@id='identifierId']")
    WebElement gmailEmail;
    private @FindBy(xpath = "//*[id='password']")
    WebElement gmailPassword;
    private @FindBy(id = "identifierNext")
    WebElement gmailNext;
    private @FindBy(id = "passwordNext")
    WebElement passwordNext;

    public ForgotPasswordPage(WebDriver driver){
        this.driver = driver;
        landingPage = new LandingPage(driver);
        PageFactory.initElements(driver, this);
    }

    public void InsertEmailAddress(String emailId){
        landingPage.waitForElementToAppear(email);
        email.clear();
        email.sendKeys(emailId);
    }

    public void goNext(){
        landingPage.waitForElementIsClickable(next);
        next.click();
        landingPage.waitForElementToAppear(notification);
    }

    public void InsertConfirmationCode(String code){
        landingPage.waitForElementToAppear(confirmationCode);
        confirmationCode.clear();
        confirmationCode.sendKeys(code);
    }

    public void InsertAndConfirmNewPassword(String newPass){
        landingPage.waitForElementToAppear(newPassword);
        newPassword.clear();
        newPassword.sendKeys(newPass);
        confirmNewPassword.clear();
        confirmNewPassword.sendKeys(newPass);
    }

    public void SubmitNewPassword(){
        landingPage.waitForElementIsClickable(submit);
        submit.click();
    }

    public String GetNotificationText(){
        landingPage.waitForElementToAppear(notification);
        return notification.getText();
    }
}
