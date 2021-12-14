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
    //test data
    String eventType = "redemption";
    String redemptionType = "Full Redemption";

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

    private void AddOrder() throws InterruptedException, AWTException {
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

    private void CreateOrder() throws IOException, InterruptedException, AWTException {
        loginPg.LoginAs("finadviser");
        Thread.sleep(200);
        ordersPg.GetLandingPage().GoToOrdersPage();
        Thread.sleep(200);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.qualis-test.com/#/orders");
        ordersPg.GetLandingPage().CloseNotification();
        ordersPg.AddNewOrder();
        AddOrder();
        //Status update and checking
        Assert.assertEquals(ordersPg.GetNotification(),"Order sent to approver");//TODO there is a typo in the notification message, it should be - "Successfully uploaded document"
    }

    @Test
    public void ProcessRedemptionOrder() throws IOException, InterruptedException, AWTException, SQLException {
        CreateOrder();
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

    @Test
    public void CancelOrder() throws InterruptedException, IOException, AWTException, SQLException {
        CreateOrder();
        CheckStatusAndLogout("Sent to Approver");
        ChangeOrderStatus("finadviser","Sent to Approver", "Cancel Order");
        int order_id = Integer.parseInt(ordersPg.GetOrderId());
        CheckStatusAndLogout("Order Cancelled");
        String query = String.format("SELECT * FROM qualis_testdb.qaip_order_mgmt ord\n" +
                "join qualis_testdb.qaip_order_status st on ord.order_status_id = st.order_status_id\n" +
                "where ord.order_id = %s", order_id);
        ResultSet orders = statement.executeQuery(query);
        orders.next();
        Assert.assertEquals("ORDER CANCELLED", orders.getString("order_status_name"));
    }
    @Test
    public void RejectOrder() throws IOException, InterruptedException, AWTException, SQLException {
        CreateOrder();
        CheckStatusAndLogout("Sent to Approver");
        ChangeOrderStatus("finadviser","Sent to Approver", "Approve");
        CheckStatusAndLogout("Sent to Qualis");
        ChangeOrderStatus("opsadmin","Sent to Qualis", "Approve");
        CheckStatusAndLogout("Sent to Fund Manager");
        ChangeOrderStatus("fund manager","Sent to Fund Manager", "Reject");
        int order_id = Integer.parseInt(ordersPg.GetOrderId());
        CheckStatusAndLogout("Order Rejected");
        String query = String.format("SELECT * FROM qualis_testdb.qaip_order_mgmt ord\n" +
                "join qualis_testdb.qaip_order_status st on ord.order_status_id = st.order_status_id\n" +
                "where ord.order_id = %s", order_id);
        ResultSet orders = statement.executeQuery(query);
        orders.next();
        Assert.assertEquals("ORDER REJECTED", orders.getString("order_status_name"));
    }

    @AfterClass
    public void CloseDriverAfterEachClassExecution(){
        logger.info(LoginTest.class.getName() + " tests have been executed.");
        if(driver != null){
            driver.close();
        }
    }
}
