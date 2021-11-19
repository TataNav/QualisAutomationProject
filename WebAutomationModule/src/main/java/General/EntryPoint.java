package General;

import PageObjects.LoginPage;
import PageObjects.OrdersPage;
import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EntryPoint {
    public static void main(String[] args) throws GeneralSecurityException, MessagingException, IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/C:/source/QualisAutomationProject/WebAutomationModule/src/main/resources/drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
        driver.get("https://www.qualis-test.com/#/login");
        LoginPage lp = new LoginPage(driver);
        lp.EnterUsername("autoqualissuperuser@praemium.com");
        lp.EnterPassword("QS@superuserPSS123!");
        lp.SignIn();
        Thread.sleep(5000);
        lp.getLandingPage().GoToOrdersPage();
        OrdersPage op = new OrdersPage(driver);
        op.AddNewOrder();

        /*
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.qualis-test.com/#/superuser");
        lp.getLandingPage().GoToOrdersPage();
        lp.getLandingPage().waitForElementIsClickable(lp.getLandingPage().getPage_Title());
        Thread.sleep(5000);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.qualis-test.com/#/orders");
        List<WebElement> listOfOrders = driver.findElements(By.id("listTable"));
        System.out.println(listOfOrders.size());
        List<WebElement> orders = driver.findElements(By.xpath("//*[@id = 'listTable']/mat-row"));
        System.out.println("orders " + orders.size());
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.qualis-test.com/#/newOrder");
        */
    }
}
