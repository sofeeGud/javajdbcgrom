package jdbc.lesson3.hw1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@database-1.clkqnh5ztnkq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "main1111";

    public List<Product> findProductsByPrice(int prise, int delta) {
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM PRODUCT WHERE PRICE BETWEEN ? AND ?")) {
            preparedStatement.setInt(1, prise - delta);
            preparedStatement.setInt(2, prise + delta);

            List<Product> products = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> findProductsByName(String word) {
        if (!validateWord(word)) {
            try (Connection conn = getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM PRODUCT WHERE NAME LIKE ?")) {
                preparedStatement.setString(1, "%" + word + "%");

                List<Product> products = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                    products.add(product);
                }

                return products;
            } catch (SQLException e) {
                System.out.println("Something went wrong");
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Product> findProductsWithEmptyDescription() {
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM PRODUCT WHERE DESCRIPTION IS NULL")) {
            List<Product> products = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private Boolean validateWord(String word) {
        boolean val = false;
        if ((word.length() < 3) || (!word.matches("[0-9a-zA-z]+"))) {
            val = true;
            System.err.println("Incorrect word " + word);
        }
        return val;
    }
}


