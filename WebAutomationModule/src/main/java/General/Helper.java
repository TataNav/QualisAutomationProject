package General;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
<<<<<<< HEAD
import com.sun.mail.util.MailSSLSocketFactory;
=======
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
<<<<<<< HEAD
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import sun.misc.IOUtils;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import java.io.*;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

public class Helper {
    private static Properties properties = new Properties();
=======

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

public class Helper {
    private static Properties properties;
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
    private static final String configFilePath = System.getProperty("user.dir") + "/src/main/resources/configs/Configuration.properties";
    private static String excelFilePath = System.getProperty("user.dir") + "/src/main/resources/qualis_users_credentials.xlsx";
    private static ExtentReports extent;
    private static String[] userCredentials = new String[2];
<<<<<<< HEAD
    private static String username = "qataskdemoaccnt@gmail.com";
    private static String password = "!Qualis1_1";
    private static String hostName = "imap.gmail.com";

    //to initialize property file to get general configs
    public static Properties InitializePropertyFile() {
        try {
=======

    //to initialize property file to get general configs
    public static Properties initializePropertyFile(){
        try {
            properties = new Properties();
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
            FileInputStream configFile = new FileInputStream(configFilePath);
            try {
                properties.load(configFile);
                configFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
<<<<<<< HEAD
        } catch (FileNotFoundException e) {
=======
        } catch(FileNotFoundException e){
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
            e.printStackTrace();
            throw new RuntimeException("Configuration file not found at " + configFilePath);
        }
        return properties;
    }
<<<<<<< HEAD

    //to apply reporting
    public static ExtentReports GetReportObject() {
=======
    //to apply reporting
    public static ExtentReports getReportObject(){
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
        String reportPath = System.getProperty("user.dir") + "//report//";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        return extent;
    }
<<<<<<< HEAD

    //to read data from excel and be able to update it
    public static String[] ReadDataFromExcel(String user) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(excelFilePath);
        XSSFSheet sheet = null;
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase(ConfigFileReader.getEnv())) {
=======
    //to read data from excel and be able to update it
    public static String[] ReadDataFromExcel(String loggedInUser) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(excelFilePath);
        XSSFSheet sheet = null;
        for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
            if(workbook.getSheetName(i).equalsIgnoreCase(ConfigFileReader.getEnv())){
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
                sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row row = rows.next();

<<<<<<< HEAD
                while (!row.getCell(0).getStringCellValue().equalsIgnoreCase(user)) {
=======
                while(!row.getCell(0).getStringCellValue().equalsIgnoreCase(loggedInUser)){
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
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
<<<<<<< HEAD

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
=======
>>>>>>> 061c94a7551b75fa73c5a89f261ae3c4b91d8330
}

