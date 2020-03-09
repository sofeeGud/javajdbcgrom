package lesson4.hw.dao;

import lesson4.hw.File;
import lesson4.hw.Storage;

import java.sql.*;

public class FileDAO implements GenDAO<File> {

    @Override
    public File save(File file) {

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO FILES VALUES(?, ?, ?, ?, ?)")) {


            preparedStatement.setLong(1, file.getId());
            preparedStatement.setString(2, file.getName());
            preparedStatement.setString(3, file.getFormat());
            preparedStatement.setLong(4, file.getSize());
            if (file.getStorage() != null) preparedStatement.setLong(5, file.getStorage().getId());
            else preparedStatement.setObject(5, null);


            int res = preparedStatement.executeUpdate();

            System.out.println("Save finished with result " + res);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return file;
    }

    @Override
    public void delete(long id) {

        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("DELETE FROM FILES WHERE ID = ?")) {

            prepareStatement.setLong(1, id);


            ValidateId(prepareStatement, id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public File update(File file) throws SQLException {

        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("UPDATE FILES SET NAME = ?, FORMAT = ?, FILE_SIZE = ?, STORAGE_ID = ? WHERE ID = ?")) {

            conn.setAutoCommit(false);

            prepareStatement.setString(1, file.getName());
            prepareStatement.setString(2, file.getFormat());
            prepareStatement.setLong(3, file.getSize());
            if (file.getStorage() != null) prepareStatement.setLong(4, file.getStorage().getId());
            else prepareStatement.setObject(4, null);
            prepareStatement.setLong(5, file.getId());


            ValidateId(prepareStatement, file.getId());

            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }

        return file;
    }

    @Override
    public File findById(long id) {

        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement
                     ("SELECT FILES.ID, FILES.NAME, FILES.FORMAT, FILES.FILE_SIZE, STORAGE.ID, STORAGE.FORMATS_SUPPORTED, STORAGE.STORAGE_COUNTRY, STORAGE.STORAGE_SIZE FROM FILES JOIN STORAGE ON FILES.STORAGE_ID = STORAGE.ID WHERE FILES.ID = ?")) {


            prepareStatement.setLong(1, id);
            ResultSet rs = prepareStatement.executeQuery();


            while (rs.next()) {


                String[] formats = rs.getString(6).split(",");
                Storage storage = new Storage(rs.getLong(5), formats, rs.getString(7), rs.getLong(8));
               File file = new File(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4), storage);


                ValidateId(prepareStatement, id);
                return file;


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private void ValidateId(PreparedStatement preparedStatement, long id) throws SQLException {
        int res = preparedStatement.executeUpdate();
        if (res == 0) System.err.println("There is no ID= " + id);

    }


}
