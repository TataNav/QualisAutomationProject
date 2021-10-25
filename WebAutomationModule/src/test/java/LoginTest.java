import General.ConfigFileReader;
import General.Helper;
import PageObjects.ForgotPasswordPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import General.BaseTest;
import PageObjects.LoginPage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginTest extends BaseTest{
    private LoginPage loginPg;
    public WebDriver driver;
    protected String url = ConfigFileReader.getURL();

    @BeforeClass()
    public void initLoginPage(){
        driver = getDriver();
        driver.get(url);
        loginPg = new LoginPage(driver);
    }

    @Test
    public void ShouldBeAbleToChangePassword() throws GeneralSecurityException, InterruptedException, MessagingException, IOException {
        String userToChangePasswordFor = "forgot password user";
        String newPassword = "newPT_" + Helper.GenerateRandomNumber() + "*" + Helper.GenerateRandomNumber();
        String[] userCredentials = Helper.ReadDataFromExcel(userToChangePasswordFor);
        ForgotPasswordPage changePass = loginPg.ResetPassword();
        changePass.InsertEmailAddress(userCredentials[0]);
        Thread.sleep(2000);
        changePass.goNext();
        Assert.assertEquals(changePass.GetNotificationText(), "Verification code sent to the registered email id");
        Thread.sleep(2000);
        String confirmationCode = Helper.GetForgotPasswordConfirmationCode();
        changePass.InsertConfirmationCode(confirmationCode);
        changePass.InsertAndConfirmNewPassword(newPassword);
        Helper.UpdatePasswordInExcel(userToChangePasswordFor, newPassword);
        changePass.SubmitNewPassword();
        //Assert.assertEquals(changePass.GetNotificationText(), "Password Update Successful! Please Login with the new Password!");
        Assert.assertEquals(changePass.GetNotificationText(), "Registration successful, pls login with the New password");
        userCredentials = Helper.ReadDataFromExcel(userToChangePasswordFor);
        loginPg.EnterUsername(userCredentials[0]);
        loginPg.EnterPassword(userCredentials[1]);
        loginPg.SignIn();
        Assert.assertEquals(loginPg.getLandingPage().getPage_Title().getText(), "Superuser");
        loginPg.getLandingPage().GoToLoggedInUserSettingsToLogout();
        Thread.sleep(2000);
    }

    @Test
    public void ShouldLoginWithValidCredentials() throws IOException {
        loginPg.EnterUsername(Helper.ReadDataFromExcel("superuser")[0]);
        loginPg.EnterPassword(Helper.ReadDataFromExcel("superuser")[1]);
        loginPg.SignIn();
        Assert.assertEquals(loginPg.getLandingPage().getPage_Title().getText(), "Superuser");
        loginPg.getLandingPage().GoToLoggedInUserSettingsToLogout();
    }

    @Test
    public void ShouldNotLoginWithInvalidCredentials() {
        loginPg.EnterUsername("Test@test");
        loginPg.EnterPassword("pass1234");
        loginPg.SignIn();
        Assert.assertEquals("The username or password you have entered is invalid.", loginPg.ValidateCredentials());
    }

    @AfterClass
    public void CloseDriverAfterEachClassExecution(){
        logger.info(LoginTest.class.getName() + " tests have been executed.");
        if(driver != null){
            driver.close();
        }
    }
}