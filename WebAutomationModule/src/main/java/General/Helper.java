package General;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

public class Helper {
    private static Properties properties;
    private static final String configFilePath = System.getProperty("user.dir") + "/src/main/resources/configs/Configuration.properties";
    private static String excelFilePath = System.getProperty("user.dir") + "/src/main/resources/qualis_users_credentials.xlsx";
    private static ExtentReports extent;
    private static String[] userCredentials = new String[2];

    //to initialize property file to get general configs
    public static Properties initializePropertyFile(){
        try {
            properties = new Properties();
            FileInputStream configFile = new FileInputStream(configFilePath);
            try {
                properties.load(configFile);
                configFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("Configuration file not found at " + configFilePath);
        }
        return properties;
    }
    //to apply reporting
    public static ExtentReports getReportObject(){
        String reportPath = System.getProperty("user.dir") + "//report//";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        return extent;
    }
    //to read data from excel and be able to update it
    public static String[] ReadDataFromExcel(String loggedInUser) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(excelFilePath);
        XSSFSheet sheet = null;
        for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
            if(workbook.getSheetName(i).equalsIgnoreCase(ConfigFileReader.getEnv())){
                sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row row = rows.next();

                while(!row.getCell(0).getStringCellValue().equalsIgnoreCase(loggedInUser)){
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
}

