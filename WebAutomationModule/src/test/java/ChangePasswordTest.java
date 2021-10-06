import General.BaseTest;
import General.ConfigFileReader;
import PageObjects.ChangePasswordPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class ChangePasswordTest extends BaseTest {
    private ChangePasswordPage changePassPg;
    protected WebDriver driver;
    protected String url = ConfigFileReader.getURL();

    @BeforeClass
    public void initChangePassPage(){
        driver = getDriver();
        driver.get(url);
        changePassPg = new ChangePasswordPage(driver);
    }

    @Test
    public void ParallelTesting() throws IOException {
        //changePassPg.getLandingPage().GoToLoggedInUserSettingsToChangePassword();
        //Assert.assertEquals(changePassPg.getLandingPage().getPage_Title().getText(), "Change Password");
        System.out.println("1st");
    }

    @Test
    public void ParallelTesting2() throws IOException {
        //changePassPg.getLandingPage().GoToLoggedInUserSettingsToChangePassword();
        //Assert.assertEquals(changePassPg.getLandingPage().getPage_Title().getText(), "Change Password");
        System.out.println("2nd");
    }

    @AfterClass
    public void CloseDriverAfterEachClassExecution(){
        logger.info(ChangePasswordTest.class.getName() + " tests have been executed.");
        if(driver != null){
            driver.close();
        }
    }
}
