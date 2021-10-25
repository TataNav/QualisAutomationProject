package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private LandingPage landingPage;
    private WebDriver driver;

    @FindBy(css = "input[type ='text'")
    private WebElement username;
    @FindBy(css = "input[type ='password'")
    private WebElement password;
    @FindBy(css = "button[type = 'submit'")
    private WebElement signInBtn;
    @FindBy(xpath = "//ul/li/notifier-notification/p")
    private WebElement notification;
    @FindBy(xpath = "//a[contains(text(),'Forgot password?')]")
    private WebElement forgotPass;
    @FindBy(tagName = "input")
    private WebElement rememberMe;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        landingPage = new LandingPage(driver);
        PageFactory.initElements(driver, this);
    }

    public LandingPage getLandingPage(){
        return landingPage;
    }

    public void EnterUsername(String userName){
        landingPage.waitForElementToAppear(username);
        username.clear();
        username.sendKeys(userName);
    }

    public void EnterPassword(String pass){
        landingPage.waitForElementToAppear(password);
        password.clear();
        password.sendKeys(pass);
    }

    public ForgotPasswordPage ResetPassword(){
        landingPage.waitForElementToAppear(forgotPass);
        forgotPass.click();
        return new ForgotPasswordPage(driver);
    }

    public void SignIn(){
        landingPage.waitForElementToAppear(signInBtn);
        signInBtn.click();
    }

    public String ValidateCredentials(){
        landingPage.waitForElementToAppear(notification);
        return notification.getText();
    }
}