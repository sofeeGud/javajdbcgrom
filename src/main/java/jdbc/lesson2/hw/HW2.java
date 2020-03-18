package jdbc.lesson2.hw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HW2 {

    private static final String DB_URL = "jdbc:oracle:thin:@database-1.clkqnh5ztnkq.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "main1111";

    public static void main(String[] args) {
        //increasePrice();
        changeDescription();
    }

    public static void increasePrice() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {

            int res = statement.executeUpdate("UPDATE PRODUCT SET PRICE=PRICE+100 WHERE PRICE < 970");
            System.out.println(res);

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public static void changeDescription() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {

            boolean res = statement.execute("UPDATE PRODUCT SET DESCRIPTION = SUBSTR(DESCRIPTION, 1, INSTR(DESCRIPTION,'.',-1)-1) WHERE LENGTH(DESCRIPTION) > 100");
            System.out.println(res);

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
