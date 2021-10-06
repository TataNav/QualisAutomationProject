import General.ConfigFileReader;
import General.Helper;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import General.BaseTest;
import PageObjects.LoginPage;

import java.io.IOException;

public class LoginTest extends BaseTest{
    private LoginPage loginPg;
    public WebDriver driver;

    @BeforeClass()
    public void initLoginPage(){
        driver = getDriver();
        driver.get(url);
        loginPg = new LoginPage(driver);
    }

    @Test()
    public void ShouldLoginWithValidCredentials() throws IOException {
        loginPg.EnterUsername(Helper.ReadDataFromExcel("superuser")[0]);
        loginPg.EnterPassword(Helper.ReadDataFromExcel("superuser")[1]);
        loginPg.SignIn();
        Assert.assertEquals(loginPg.getLandingPage().getPage_Title().getText(), "Superuser");
    }

    @Test(dependsOnMethods = "Logout")
    public void ShouldNotLoginWithInvalidCredentials() {
        loginPg.EnterUsername("Test@test");
        loginPg.EnterPassword("pass1234");
        loginPg.SignIn();
        Assert.assertEquals("The username or password you have entered is invalid.", loginPg.ValidateCredentials());
    }

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

    @AfterClass
    public void CloseDriverAfterEachClassExecution(){
        logger.info(LoginTest.class.getName() + " tests have been executed.");
        if(driver != null){
            driver.close();
        }
    }
}