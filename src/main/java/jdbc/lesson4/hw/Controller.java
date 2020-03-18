package jdbc.lesson4.hw;

import jdbc.lesson4.hw.dao.FileDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private FileDAO fileDAO = new FileDAO();
    private static final String DB_URL = "jdbc:oracle:thin:@database-1.clkqnh5ztnkq.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "main1111";


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


    public File put(Storage storage, File file) throws Exception {


        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("UPDATE FILES SET STORAGE_ID = ? WHERE ID = ?")) {

            conn.setAutoCommit(false);

            if (validateStorageSize(storage, file)) {
                prepareStatement.setLong(1, storage.getId());
                prepareStatement.setLong(2, file.getId());
                int res = prepareStatement.executeUpdate();
                System.out.println("File " + file.getName() + "." + file.getFormat() + " put to storage " + storage.getId() + " with a result " + res);
                conn.commit();
            } else throw new Exception("Something wrong");


        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }

        System.out.println(file.toString());
        return file;
    }

    public List<File> putAll(Storage storage, List<File> files) throws Exception {


        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("UPDATE FILES SET STORAGE_ID = ? WHERE ID=? ")) {

            conn.setAutoCommit(false);
            validateFilesSize(files, storage);
            for (File file : files) {
                if (file != null) {
                    prepareStatement.setLong(1, storage.getId());
                    prepareStatement.setLong(2, file.getId());
                    int res = prepareStatement.executeUpdate();
                    System.out.println("File " + file.getName() + "." + file.getFormat() + " put to storage " + storage.getId() + " with result " + res);
                    conn.commit();
                } else conn.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }


        return files;
    }

    public void delete(Storage storage, File file) throws Exception {


        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("DELETE FROM FILES WHERE ID = ? AND STORAGE_ID=? ")) {

            conn.setAutoCommit(false);


            prepareStatement.setLong(1, file.getId());
            prepareStatement.setObject(2, storage.getId());
            int res = prepareStatement.executeUpdate();
            conn.commit();
            System.out.println("File " + file.getName() + "." + file.getFormat() + " deleted with result " + res);
            System.out.println(file.toString());


        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }


    }

    public List<File> transferAll(Storage storageFrom, Storage storageTo) throws Exception {

        List<File> files = getFiles(storageFrom);


        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement
                     ("UPDATE FILES SET STORAGE_ID = (SELECT ID FROM STORAGE WHERE ID = ?) WHERE FILES.STORAGE_ID = ?")) {


            conn.setAutoCommit(false);
            for (File file : files) {
                if (validateFilesSize(files, storageTo)) {
                    prepareStatement.setLong(1, storageTo.getId());
                    prepareStatement.setLong(2, storageFrom.getId());
                    int res = prepareStatement.executeUpdate();
                    System.out.println("File " + file.getName() + "." + file.getFormat() + " transferred to storage " + storageTo.getId() + "  with result " + res);
                    conn.commit();
                } else {
                    System.err.println("Not enough space in storage " + storageTo.getId());
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }

        return files;
    }

    public File transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {

        File file = fileDAO.findById(id);
        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement
                     ("UPDATE FILES SET STORAGE_ID = ? WHERE ID= ? AND STORAGE_ID = ?")) {
            conn.setAutoCommit(false);
            if (!validateStorageSize(storageTo, file))

            throw new Exception("Not enough space for file " + file.getName() + "." + file.getFormat() + " in storage " + storageTo.getId());
        else {
                    prepareStatement.setLong(1, storageTo.getId());
                    prepareStatement.setLong(2, file.getId());
                    prepareStatement.setLong(3, storageFrom.getId());
                    int res = prepareStatement.executeUpdate();
            System.out.println("File " + file.getName() + "." + file.getFormat() + " transferred to storage " + storageTo.getId() + "  with result " + res);


        }
        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }

        return file;
    }


    private boolean validateStorageSize(Storage storage, File file) throws Exception {

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

    private boolean validateFilesSize(List<File> files, Storage storage) throws Exception {

        long filesTotalSize = 0;

        for (File file : files) {
            filesTotalSize += file.getSize();

        }

        if (storage != null)
            return ((storage.getStorageMaxSize() - getFilesSize(storage) > filesTotalSize));
        else throw new Exception("Something wrong");


    }

    private List<File> getFiles(Storage storage) {

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

}
