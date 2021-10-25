package General;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

public class ConfigFileReader {
    private static Properties properties = Helper.InitializePropertyFile();

    private static String  env;
    private static String url;
    private static String shortenedURL;
    private static String browserName;
    private static int implicitlyWait;
    private static WebDriver driver;
    private static String projectPath;

    private static String getBrowserName(){
        //there is an option to send the browser name from cmd
        //to do that it is required to execute the following maven command from the cmd
        //mvn test -Dbrowser-chrome
        //later the given browser name will be taken in the following way
        if (System.getProperty("browser") != null){
            browserName = System.getProperty("browser");
        } else {
            browserName = properties.getProperty("browser");
        }
        if (browserName != null) return browserName;
        else throw new RuntimeException("Browser not specified in the Configuration.properties file.");
    }

    public static String getEnv(){
        env = properties.getProperty("env");
        if (env != null) return env;
        else throw new RuntimeException("The test environment is not specified in the Configuration.properties file.");
    }

    public static int getImplicitWait(){
        implicitlyWait = Integer.parseInt(properties.getProperty("implicitlyWait"));
        if (implicitlyWait != 0) return implicitlyWait;
        else throw new RuntimeException();
    }

    public static WebDriver initializeDriver() {
        switch(getBrowserName()){
            case "chrome":
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/drivers/chromedriver.exe");
                //it is allowed to execute tests in headless mode as well, depends on the command coming from the cmd
                //command should be - mvn test -Dbrowser=chromeheadless
                ChromeOptions options = new ChromeOptions();
                if(getBrowserName().contains("headless")) {
                    options.addArguments("headless");
                }
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/main/resources/drivers/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
        }
        return driver;
    }

    private static String initializeURL(){
        switch (getEnv()) {
            case "test":
                try {
                    url = properties.getProperty("testEnv");
                    break;
                } catch (Exception e) {
                    throw new RuntimeException("URL not specified in the Configuration.properties file.");
                }
            case "uat":
                try {
                    url = properties.getProperty("uatEnv");
                    break;
                } catch (Exception e) {
                    throw new RuntimeException("URL not specified in the Configuration.properties file.");
                }
        }
        return url;
    }

    private static String initializeShortenedURL(){
        switch (getEnv()) {
            case "test":
                try {
                    shortenedURL = properties.getProperty("shortenedTest_URL");
                    break;
                } catch (Exception e) {
                    throw new RuntimeException("URL not specified in the Configuration.properties file.");
                }
            case "uat":
                try {
                    shortenedURL = properties.getProperty("shortenedUAT_URL");
                    break;
                } catch (Exception e) {
                    throw new RuntimeException("URL not specified in the Configuration.properties file.");
                }
        }
        return shortenedURL;
    }

    public static String getProjectPath(){
        projectPath = properties.getProperty("projectPath");
        if (projectPath != null) return projectPath;
        else throw new RuntimeException("No project path has been defined");
    }

    public static String getURL(){
        return initializeURL();
    }

    public static String getShortenedURL() {
        return initializeShortenedURL();
    }
/*
    public static String getTestEnv(){
        return getEnv();
    }
 */
}
