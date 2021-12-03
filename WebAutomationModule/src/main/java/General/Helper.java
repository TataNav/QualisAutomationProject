package General;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

public class Helper {
    private static Properties properties = new Properties();
    private static final String configFilePath = System.getProperty("user.dir") + "/src/main/resources/configs/Configuration.properties";
    private static String excelFilePath = System.getProperty("user.dir") + "/src/main/resources/qualis_users_credentials.xlsx";
    private static ExtentReports extent;
    private static String[] userCredentials = new String[2];
    private static String username = "qataskdemoaccnt@gmail.com";
    private static String password = "!Qualis1_1";
    private static String hostName = "imap.gmail.com";
    protected static Logger logger = LogManager.getLogger(BaseTest.class.getName());

    //to initialize property file to get general configs
    public static Properties InitializePropertyFile() {
        try {
            FileInputStream configFile = new FileInputStream(configFilePath);
            try {
                properties.load(configFile);
                configFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration file not found at " + configFilePath);
        }
        return properties;
    }

    //to apply reporting
    public static ExtentReports GetReportObject() {
        String reportPath = System.getProperty("user.dir") + "//report//";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        return extent;
    }

    //to read data from excel and be able to update it
    public static String[] ReadDataFromExcel(String user) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(excelFilePath);
        XSSFSheet sheet = null;
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase(ConfigFileReader.getEnv())) {
                sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row row = rows.next();

                while (!row.getCell(0).getStringCellValue().equalsIgnoreCase(user)) {
                    row = rows.next();
                }
                Iterator<Cell> cell = row.cellIterator();
                cell.next();
                userCredentials[0] = cell.next().getStringCellValue();
                userCredentials[1] = cell.next().getStringCellValue();
            }
        }
        return userCredentials;
    }

    public static void UpdatePasswordInExcel(String user, String newPassword) throws IOException {
        FileInputStream fis = new FileInputStream(excelFilePath);

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = null;
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase(ConfigFileReader.getEnv())) {
                sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row row = rows.next();

                while (!row.getCell(0).getStringCellValue().equalsIgnoreCase(user)) {
                    row = rows.next();
                }
                Iterator<Cell> cell = row.cellIterator();
                cell.next();
                System.out.println(cell.next().getStringCellValue());
                cell.next().setCellValue(newPassword);

            }
        }
        FileOutputStream fos = new FileOutputStream(excelFilePath);
        workbook.write(fos);
        fos.close();
    }

    public static String GetForgotPasswordConfirmationCode() throws MessagingException {
        String result = "", substring = "Confirmation";
        int firstIndex = 0, lastIndex = 0;
        properties.put("mail.imap.host", hostName);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.starttls.enable", "true");
        properties.put("mail.imap.ssl.trust", hostName);
        properties.put("mail.debug", "true");

        Session emailSession = Session.getDefaultInstance(properties);

        Store store = emailSession.getStore("imaps");
        store.connect(hostName, username, password);

        Folder inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_WRITE);
        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
        System.out.println("messages.length---" + messages.length);

        try {
            for (Message message : messages) {
                if(message.getSubject().equalsIgnoreCase("Qualis Capital - Password Recovery")){
                    MimeMessage m = (MimeMessage) message;
                    Object contentObject = m.getContent();
                    if (contentObject instanceof Multipart) {
                        BodyPart clearTextPart = null;
                        Multipart content = (Multipart) contentObject;
                        int count = content.getCount();
                        for (int i = 0; i < count; i++) {
                            BodyPart part = content.getBodyPart(i);
                            clearTextPart = part;
                        }
                        if (clearTextPart != null) {
                            result = (String) clearTextPart.getContent();
                        }
                    } else if (contentObject instanceof String) {
                        result = (String) contentObject;
                    }
                }
            }
            firstIndex = FindSubstringInAString(result, substring, 'n') + 12;
            lastIndex = firstIndex + 6;
            inbox.close(false);
            store.close();
            result = result.substring(firstIndex, lastIndex);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("No forgot password request found in the recent mails. Resend the request from GUI.");
        }
    }

    public static int GenerateRandomNumber(){
        Random random = new Random();
        int randomNum = random.nextInt(900) + 15;
        return randomNum;
    }

    public static int FindSubstringInAString(String mainString, String substring, char charToGetIndexFor){
        int firstIndexOfSubstring = 0, indexOfSpecificCharInSubstring = 0;
        firstIndexOfSubstring = mainString.indexOf(substring);
        indexOfSpecificCharInSubstring = substring.lastIndexOf(charToGetIndexFor);
        return firstIndexOfSubstring + indexOfSpecificCharInSubstring;
    }

    public static void FileUpload_Robo(String filepath) throws AWTException {
        Robot robot = new Robot();
        //ctrl C
        StringSelection stringSelection = new StringSelection(filepath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        robot.setAutoDelay(2000);
        //ctrl V
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        robot.setAutoDelay(2000);
        //press enter after choosing the file
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static Connection ConnectToDatabase(){
        String hostname = ConfigFileReader.GetHostname();
        String username = ConfigFileReader.GetDatabaseUsername();
        String password = ConfigFileReader.GetDatabasePassword();
        String driver = ConfigFileReader.GetDatabaseDriver();
        Connection connection = null;
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(hostname,username,password);
            logger.info("Connected to the database.");
            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Unable to establish connection.");
        }
    }
}

