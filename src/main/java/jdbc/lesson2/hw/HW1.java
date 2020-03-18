package jdbc.lesson2.hw;

import java.sql.*;

public class HW1 {
    private static final String DB_URL = "jdbc:oracle:thin:@database-1.clkqnh5ztnkq.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "main1111";

    public static void main(String[] args) {
        getAllProducts();
        getProductsByPrice();
        getProductsByDescription();

    }

    public static void getAllProducts() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {


            ResultSet res = statement.executeQuery("SELECT * FROM PRODUCT");
            while (res.next()) {
                System.out.println(getProducts(res));
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public static void getProductsByPrice() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {


            ResultSet res = statement.executeQuery("SELECT * FROM PRODUCT WHERE PRICE < 100");
            while (res.next()) {
                System.out.println(getProducts(res));
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public static void getProductsByDescription() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {


            ResultSet res = statement.executeQuery("SELECT * FROM PRODUCT WHERE LENGTH (DESCRIPTION) > 50");
            while (res.next()) {
                System.out.println(getProducts(res));
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }


    private static Product getProducts(ResultSet res) throws SQLException {
        long id = res.getLong(1);
        String name = res.getString(2);
        String description = res.getString(3);
        int price = res.getInt(4);
        return new Product(id, name, description, price);
    }
}

