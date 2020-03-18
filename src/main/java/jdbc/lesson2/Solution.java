package jdbc.lesson2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@database-1.clkqnh5ztnkq.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "main1111";

    public void saveProduct() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {

            boolean res = statement.execute("INSERT INTO PRODUCT VALUES(2, 'toy111', 'for children', 60)");
            System.out.println(res);

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void deleteProducts() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {

            int res = statement.executeUpdate("DELETE FROM PRODUCT WHERE NAME !='toy'");
            System.out.println(res);

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void deleteProductsByPrice() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {

            boolean res = statement.execute("DELETE FROM PRODUCT WHERE PRICE < 100");
            System.out.println(res);

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
