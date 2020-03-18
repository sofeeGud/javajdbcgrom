package jdbc.lesson4.hw.dao;

import java.sql.*;

public interface GenDAO<T> {

    public static final String DB_URL = "jdbc:oracle:thin:@database-1.clkqnh5ztnkq.us-east-2.rds.amazonaws.com:1521:ORCL";
    public static final String USER = "main";
    public static final String PASS = "main1111";

    T save(T t);

    void delete(long id);

    T update(T t) throws SQLException;

    T findById(long id);

}
