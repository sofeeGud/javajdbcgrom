package jdbc.lesson4.hw.dao;

import jdbc.lesson4.hw.File;
import jdbc.lesson4.hw.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileDAO implements GenDAO<File> {

    @Override
    public File save(File file) throws Exception {

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO FILES VALUES(?, ?, ?, ?, ?)")) {
            conn.setAutoCommit(false);

            if (validateStorageSize(file.getStorage(), file)) {
                preparedStatement.setLong(1, file.getId());
                preparedStatement.setString(2, file.getName());
                preparedStatement.setString(3, file.getFormat());
                preparedStatement.setLong(4, file.getSize());
                preparedStatement.setLong(5, file.getStorage().getId());
                int res = preparedStatement.executeUpdate();
                System.out.println("Save finished with result " + res);
                conn.commit();
            } else throw new Exception("Something wrong");

        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }

        return file;
    }

    @Override
    public void delete(long id) throws Exception {

        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("DELETE FROM FILES WHERE ID = ?")) {
            conn.setAutoCommit(false);
            prepareStatement.setLong(1, id);
            ValidateId(prepareStatement, id);
            int res = prepareStatement.executeUpdate();
            System.out.println("Delete finished with result " + res);
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
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
            prepareStatement.setLong(4, file.getStorage().getId());
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
        if (res == 0) System.err.println("There is not ID= " + id);

    }

    public boolean validateStorageSize(Storage storage, File file) throws Exception {

        if (storage != null && file != null)
            return ((storage.getStorageMaxSize() - getFilesSize(storage)) > file.getSize());
        else if (storage == null) throw new Exception("There is not storage " + storage.getId());
        else throw new Exception("Something wrong");
    }

    private long getFilesSize(Storage storage) {

        long filesTotalSize = 0;


        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM FILES WHERE STORAGE_ID = ?")) {

            prepareStatement.setLong(1, storage.getId());

            ResultSet rs = prepareStatement.executeQuery();

            while (rs.next()) {
                filesTotalSize += rs.getLong(4);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return filesTotalSize;
    }

    public List<File> getFiles(Storage storage) {

        List<File> files = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM FILES WHERE STORAGE_ID = ?")) {

            prepareStatement.setLong(1, storage.getId());

            ResultSet rs = prepareStatement.executeQuery();

            while (rs.next()) {
                File file = new File(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4), storage);
                files.add(file);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return files;
    }

    public File put(Storage storage, File file) throws Exception {

        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("UPDATE FILES SET STORAGE_ID = ? WHERE ID = ?")) {

            conn.setAutoCommit(false);
            if (file != null && storage != null) {
                prepareStatement.setLong(1, storage.getId());
                prepareStatement.setLong(2, file.getId());
                int res = prepareStatement.executeUpdate();
                System.out.println("Put finished with result " + res + " file_id= " + file.getId());
                conn.commit();
            } else throw new Exception("Something wrong");


        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }

        return file;
    }

}
