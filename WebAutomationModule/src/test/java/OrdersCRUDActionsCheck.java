import General.BaseTest;
import General.ConfigFileReader;
import General.Helper;
import PageObjects.LoginPage;
import PageObjects.OrdersPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;

public class OrdersCRUDActionsCheck extends BaseTest {
    private OrdersPage ordersPg;
    private WebDriver driver;
    protected String url = ConfigFileReader.getURL();
    private LoginPage loginPg;
    Actions actions;

    @BeforeClass()
    public void OrdersTestSetup(){
        driver = getDriver();
        driver.get(url);
        ordersPg = new OrdersPage(driver);
        loginPg = new LoginPage(driver);
        actions = new Actions(driver);
    }

    private void ChangeOrderStatus(String loginUser, String statusToCheck, String action) throws InterruptedException, IOException {
        loginPg.LoginAs(loginUser);
        ordersPg.GetLandingPage().CloseNotification();
        ordersPg.GetLandingPage().GoToOrdersPage();
        Thread.sleep(100);
        Assert.assertEquals(ordersPg.GetStatusOfOrder(), statusToCheck);
        ordersPg.EditLastOrder(action);
    }

    private void CheckStatusAndLogout(String statusToCheck) throws IOException {
        String status = ordersPg.GetStatusOfOrder();
        Assert.assertEquals(status,statusToCheck);
        ordersPg.GetLandingPage().GoToLoggedInUserSettingsToLogout();
    }

    @Test
    public void CreateRedemptionOrder() throws IOException, InterruptedException, AWTException {
        String eventType = "redemption";
        String redemptionType = "Full Redemption";
        loginPg.LoginAs("finadviser");
        Thread.sleep(200);
        //Assert.assertEquals(driver.getCurrentUrl(), "https://www.qualis-test.com/#/");
        ordersPg.GetLandingPage().GoToOrdersPage();
        Thread.sleep(200);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.qualis-test.com/#/orders");
        ordersPg.GetLandingPage().CloseNotification();
        ordersPg.AddNewOrder();
        //Order details
        ordersPg.AddFundName("FOR TESTING");
        ordersPg.AddInvestorIdentification("AUTO INVESTOR");
        ordersPg.SelectEvent(eventType);
        ordersPg.InsertEventDetails(redemptionType);
        ordersPg.PickDate("11/2021");
        ordersPg.AddComment("Automation test");
        //Documentation
        ordersPg.Save();
        ordersPg.AddDocument();
        ordersPg.RequestApproval();
        //Status update and checking
        Assert.assertEquals(ordersPg.GetNotification(),"Order sent to approver");//TODO there is a typo in the notification message, it should be - "Successfully uploaded document"
        CheckStatusAndLogout("Sent to Approver");
        ChangeOrderStatus("finadviser","Sent to Approver", "Approve");
        CheckStatusAndLogout("Sent to Qualis");
        ChangeOrderStatus("opsadmin","Sent to Qualis", "Approve");
        CheckStatusAndLogout("Sent to Fund Manager");
        ChangeOrderStatus("fund manager","Sent to Fund Manager", "Approve");
        CheckStatusAndLogout("Order Accepted");
        ChangeOrderStatus("finadviser","Order Accepted", "Confirm Funds Transferred");
        CheckStatusAndLogout("Funds Transferred");
        ChangeOrderStatus("fund manager","Funds Transferred", "Confirm Funds Received");
        ordersPg.FilterCompletedOrder();
        Assert.assertEquals(ordersPg.GetStatusOfOrder(), "Order Completed");
    }
    @AfterClass
    public void CloseDriverAfterEachClassExecution(){
        logger.info(LoginTest.class.getName() + " tests have been executed.");
        if(driver != null){
            driver.close();
        }
    }
}
