import General.ConfigFileReader;
import General.Helper;
<<<<<<< HEAD
import PageObjects.ForgotPasswordPage;
=======
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import General.BaseTest;
import PageObjects.LoginPage;

<<<<<<< HEAD
import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
=======
import java.io.IOException;
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330

public class LoginTest extends BaseTest{
    private LoginPage loginPg;
    public WebDriver driver;
<<<<<<< HEAD
    protected String url = ConfigFileReader.getURL();
=======
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330

    @BeforeClass()
    public void initLoginPage(){
        driver = getDriver();
        driver.get(url);
        loginPg = new LoginPage(driver);
    }

<<<<<<< HEAD
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
=======
    @Test()
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
    public void ShouldLoginWithValidCredentials() throws IOException {
        loginPg.EnterUsername(Helper.ReadDataFromExcel("superuser")[0]);
        loginPg.EnterPassword(Helper.ReadDataFromExcel("superuser")[1]);
        loginPg.SignIn();
        Assert.assertEquals(loginPg.getLandingPage().getPage_Title().getText(), "Superuser");
<<<<<<< HEAD
        loginPg.getLandingPage().GoToLoggedInUserSettingsToLogout();
    }

    @Test
=======
    }

    @Test(dependsOnMethods = "Logout")
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
    public void ShouldNotLoginWithInvalidCredentials() {
        loginPg.EnterUsername("Test@test");
        loginPg.EnterPassword("pass1234");
        loginPg.SignIn();
        Assert.assertEquals("The username or password you have entered is invalid.", loginPg.ValidateCredentials());
    }

<<<<<<< HEAD
=======
    @Test
    public void NavigateThroughAllPages() throws IOException {
        loginPg.EnterUsername("tatevik.navasardyan@praemium.com");
        loginPg.EnterPassword("Drwho!1Qualis");
        loginPg.SignIn();

        //Assert.assertEquals(loginPg.getLandingPage().GoToDashboard(), "Dashboard");

        Assert.assertEquals(loginPg.getLandingPage().GoToFundsPage(), "Funds");

        Assert.assertEquals(loginPg.getLandingPage().GoToHoldingsPage(), "Holdings");

        Assert.assertEquals(loginPg.getLandingPage().GoToOrdersPage(), "Orders");

        //Assert.assertEquals(lp.getHomePage().GoToInvestorsApprovalPage(), "Investors");//this should be changed later to - "Approval Requested Investors"

        //Assert.assertEquals(lp.getHomePage().GoToFundApprovalPage(), "Fund Approval");

        Assert.assertEquals(loginPg.getLandingPage().GoToUserManagementPage(), "User");//this should be changed later to - "User"

        Assert.assertEquals(loginPg.getLandingPage().GoToRoleAccessPage(), "Role");

        Assert.assertEquals(loginPg.getLandingPage().GoToTeamManagementPage(), "Team");

        Assert.assertEquals(loginPg.getLandingPage().GoToFundApprovalPage(), "Fund Approval");

        Assert.assertEquals(loginPg.getLandingPage().GoToActivityLogsPage(), "Activity Logs");

        //Assert.assertEquals(loginPg.getLandingPage().GoToAPLPage(), "APL");

        //Assert.assertEquals(loginPg.getLandingPage().GoToEventsPage(), "Events");

        Assert.assertEquals(loginPg.getLandingPage().GoToOrganizationPage(), "Organization");
        loginPg.getLandingPage().GoToLoggedInUserSettingsToLogout();
    }

    @Test(dependsOnMethods = "ShouldLoginWithValidCredentials")
    public void ChangePassword() throws IOException {
        Assert.assertEquals(loginPg.getLandingPage().GoToLoggedInUserSettingsToChangePassword(), "Change Password");
        loginPg.getLandingPage().GoToLoggedInUserSettingsToLogout();
    }

    public void CheckNotifications() throws IOException {
        loginPg.getLandingPage().GetOrdersNotifications();
        loginPg.getLandingPage().GetFundsNotifications();
        loginPg.getLandingPage().GoToLoggedInUserSettingsToChangePassword();
        Assert.assertEquals(loginPg.getLandingPage().GoToLoggedInUserSettingsToChangePassword(), "Change Password");
        loginPg.getLandingPage().GoToLoggedInUserSettingsToLogout();
        loginPg.getLandingPage().GoToLoggedInUserSettingsToLogout();
    }

    @Test
    public void Logout() throws IOException {
        loginPg.EnterUsername("autoqualissuperuser@praemium.com");
        loginPg.EnterPassword("QS@superuserPSS123!");
        loginPg.SignIn();
        loginPg.getLandingPage().GoToLoggedInUserSettingsToLogout();
        //Assert.assertEquals(getDriver().getCurrentUrl(), driver.get(ConfigFileReader.getURL()));
    }

>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
    @AfterClass
    public void CloseDriverAfterEachClassExecution(){
        logger.info(LoginTest.class.getName() + " tests have been executed.");
        if(driver != null){
            driver.close();
        }
    }
}