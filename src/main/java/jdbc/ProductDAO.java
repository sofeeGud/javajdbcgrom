package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@database-1.clkqnh5ztnkq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "main1111";

    public Product save(Product product) {

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO PRODUCT VALUES(?, ?, ?, ?)")) {

            preparedStatement.setLong(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getPrice());
            int res = preparedStatement.executeUpdate();
            System.out.println("Save was finished with result " + res);
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return product;
    }

    public Product update(Product product) {

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("UPDATE PRODUCT SET NAME = ?, DESCRIPTION = ?, PRICE = ? WHERE ID = ?")) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getPrice());
            preparedStatement.setLong(4, product.getId());
            int res = preparedStatement.executeUpdate();
            System.out.println("Update was finished with result " + res);
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> getProducts() {
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT");

            List<Product> products = new ArrayList<>();
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

    public void delete(long id) {

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM PRODUCT WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            int res = preparedStatement.executeUpdate();
            System.out.println("Delete was finished with result " + res);
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
/*
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {

           *//* boolean res = statement.execute("INSERT INTO PRODUCT VALUES(2, 'toy111', 'for children', 60)");
            System.out.println(res);*//*

     *//*boolean res = statement.execute("DELETE FROM PRODUCT WHERE NAME ='toy111'");
            System.out.println(res);*//*

            int response = statement.executeUpdate("INSERT INTO PRODUCT VALUES(5, 'car', 'for children', 770)");
            System.out.println(response);
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }*/
}
