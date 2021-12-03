package General;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected static Logger logger = LogManager.getLogger(BaseTest.class.getName());
    private WebDriver driver;
    protected Connection connection = null;

    @BeforeSuite
    public void beforeSuite(){
        logger.info("Start the test suite execution");
    }

    @BeforeClass
    public void beforeClass(){
        driver = ConfigFileReader.initializeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(ConfigFileReader.getImplicitWait(), TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        logger.info("Before CLASS setup is successfully done");
    }

    @AfterSuite
    public void afterSuite() throws SQLException {
        logger.info("The test suite execution has just completed.");
        if(driver != null){
            driver.quit();
        }
        connection.close();
        logger.info("Disconnected from the database.");
    }

    public WebDriver getDriver() {
        return driver;
    }
}
