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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrdersCRUDActionsCheck extends BaseTest {
    private OrdersPage ordersPg;
    public WebDriver driver;
    protected String url = ConfigFileReader.getURL();
    private LoginPage loginPg;
    Actions actions;
    private Statement statement;

    @BeforeClass()
    public void OrdersTestSetup() throws SQLException {
        driver = getDriver();
        driver.get(url);
        ordersPg = new OrdersPage(driver);
        loginPg = new LoginPage(driver);
        actions = new Actions(driver);
        connection = Helper.ConnectToDatabase();
        statement = connection.createStatement();
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
    public void CreateRedemptionOrder() throws IOException, InterruptedException, AWTException, SQLException {
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
        String query = "SELECT order_id FROM qualis_testdb.qaip_order_mgmt order by order_id desc Limit 1";
        ResultSet orders = statement.executeQuery(query);
        orders.next();
        Assert.assertEquals(ordersPg.GetOrderId(), orders.getString("order_id"));
    }
    @AfterClass
    public void CloseDriverAfterEachClassExecution(){
        logger.info(LoginTest.class.getName() + " tests have been executed.");
        if(driver != null){
            driver.close();
        }
    }
}
