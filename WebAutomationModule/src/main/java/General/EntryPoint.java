package General;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.*;
import java.sql.DriverManager;

public class EntryPoint {
    public static void main(String[] args) throws GeneralSecurityException, MessagingException, IOException, InterruptedException {
        System.out.println("MySQL Connect Example.");
        Connection conn = null;
        String url = "jdbc:mysql://qualistestdb.cxfffvyuewsj.us-east-2.rds.amazonaws.com:3306/qualis_testdb";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "admin";
        String password = "qualistestdb$123";
        try {
            //loads the driver
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url,userName,password);
            System.out.println("Connected to the database");
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT  * FROM qualis_testdb.qaip_order_mgmt where created_date > '2021-11-17'");
            results.next();
            System.out.println(results.getString("order_id"));
            conn.close();
            System.out.println("Disconnected from database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
