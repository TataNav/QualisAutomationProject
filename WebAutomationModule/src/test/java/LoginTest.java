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
import java.sql.SQLException;
import java.sql.Statement;

public class LoginTest extends BaseTest{
    private LoginPage loginPg;
    public WebDriver driver;
    private String url = ConfigFileReader.getURL();
    private Statement statement;

    @BeforeClass()
    public void LoginTestSetup() throws SQLException {
        driver = getDriver();
        driver.get(url);
        loginPg = new LoginPage(driver);
        connection = Helper.ConnectToDatabase();
        statement = connection.createStatement();
    }

    @Test
    public void ResetPassword() throws InterruptedException, MessagingException, IOException {
        String resetPassUser = "forgot password user";
        String newPassword = "newPT_" + Helper.GenerateRandomNumber() + "*" + Helper.GenerateRandomNumber();
        ForgotPasswordPage changePass = loginPg.ResetPassword();
        changePass.InsertEmailAddress(Helper.ReadDataFromExcel(resetPassUser)[0]);
        changePass.goNext();
        Thread.sleep(1000);
        Assert.assertEquals(changePass.GetNotificationText(), "Verification code sent to the registered email id");
        Thread.sleep(500);
        String confirmationCode = Helper.GetForgotPasswordConfirmationCode();
        changePass.InsertConfirmationCode(confirmationCode);
        changePass.InsertAndConfirmNewPassword(newPassword);
        Helper.UpdatePasswordInExcel(resetPassUser, newPassword);
        changePass.SubmitNewPassword();
        Assert.assertEquals(changePass.GetNotificationText(), "Registration successful, pls login with the New password");
        loginPg.LoginAs(resetPassUser);
        Assert.assertEquals(loginPg.getLandingPage().getPage_Title().getText(), "Superuser");
        loginPg.getLandingPage().GoToLoggedInUserSettingsToLogout();
        Thread.sleep(500);
    }

    @Test
    public void LoginWithValidCredentials() throws IOException {
        loginPg.LoginAs("superuser");
        Assert.assertEquals(loginPg.getLandingPage().getPage_Title().getText(), "Superuser");
        loginPg.getLandingPage().GoToLoggedInUserSettingsToLogout();
    }

    @Test
    public void LoginWithInvalidCredentials() throws IOException {
        loginPg.LoginAs("invalid user");
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