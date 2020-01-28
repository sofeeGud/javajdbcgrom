import java.sql.*;

public class JDBCFirstStep {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@//database-1.clkqnh5ztnkq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "main1111";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {
            //1. DB driver
            //2. Create connection
            //3. Create query
            //4. Execute query
            //5. Work with result
            //6. Close all connections

            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                System.out.println("Class " + JDBC_DRIVER + " not found");
                return;
            }
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM ORDERS")) {
                while (resultSet.next()) {
                    //todo
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
