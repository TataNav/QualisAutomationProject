package General;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected static Logger logger = LogManager.getLogger(BaseTest.class.getName());
<<<<<<< HEAD
=======
    protected String url = ConfigFileReader.getURL();
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
    private WebDriver driver;

    @BeforeSuite
    public void beforeSuite(){
        logger.info("This is to track the suite execution start time");
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
    public void afterSuite(){
        logger.info("This is to track the suite completion time");
        if(driver != null){
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
