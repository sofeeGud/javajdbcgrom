package lesson3.hw2;

import java.sql.*;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;


public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@database-1.clkqnh5ztnkq.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "main1111";


    public void testSavePerformance() throws Exception {
        //Time test save performance: 140277
        Date start = new Date(), end;
        long time;
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO TEST_SPEED VALUES(?, ?, ?)")) {
            for (int i = 1; i <= 1000; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "testSavePerformance" + i);
                preparedStatement.setInt(3, ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
                if (preparedStatement.executeUpdate() == 0)
                    throw new Exception("row " + i + " was not inserted");
            }
        } catch (SQLException e) {
            System.err.println("something went wrong");
            throw e;
        }
        end = new Date();
        time = end.getTime() - start.getTime();
        System.out.println("Time test save performance: " + time);

    }

    public void testDeleteByIdPerformance() throws Exception {
        //Time test delete by Id performance: 151087
        Date start = new Date(), end;
        long time;
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM TEST_SPEED WHERE ID = ?")) {
            for (int i = 1; i <= 1000; i++) {
                preparedStatement.setInt(1, i);
                if (preparedStatement.executeUpdate() == 0)
                    throw new Exception("row " + i + " was not deleted");
            }
        } catch (SQLException e) {
            System.err.println("something went wrong");
            throw e;
        }
        end = new Date();
        time = end.getTime() - start.getTime();
        System.out.println("Time test delete by Id performance: " + time);
    }

    public void testDeletePerformance() throws Exception {
        //Time test delete performance: 6093
        Date start = new Date(), end;
        long time;
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            if (stmt.executeUpdate("DELETE FROM TEST_SPEED WHERE ID <= 1000") == 0)
                throw new Exception("no rows was updated");
        } catch (SQLException e) {
            System.err.println("something went wrong");
            throw e;
        }
        end = new Date();
        time = end.getTime() - start.getTime();
        System.out.println("Time test delete performance: " + time);
    }

    public void testSelectByIdPerformance() throws SQLException {
        //Time test select by Id performance: 151719
        Date start = new Date(), end;
        long time;
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM TEST_SPEED WHERE ID = ?")) {
            for (int i = 1; i <= 1000; i++) {
                preparedStatement.setInt(1, i);
                ResultSet resultSet = preparedStatement.executeQuery();
            }
        } catch (SQLException e) {
            System.err.println("something went wrong");
            throw e;
        }
        end = new Date();
        time = end.getTime() - start.getTime();
        System.out.println("Time test select by Id performance: " + time);
    }

    public void testSelectPerformance() throws SQLException {
        //Time test select performance: 14135
        Date start = new Date(), end;
        long time;
        try (Connection conn = getConnection(); Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TEST_SPEED WHERE ID <= 1000");
        } catch (SQLException e) {
            System.err.println("something went wrong");
            throw e;
        }
        end = new Date();
        time = end.getTime() - start.getTime();
        System.out.println("Time test select performance: " + time);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
