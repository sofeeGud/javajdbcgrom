package lesson4.hw;

import lesson4.hw.dao.FileDAO;

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

            if (validateStorageSize(storage, file) && !fileInStorage(storage, file)) {

                file.setStorage(storage);
                prepareStatement.setLong(1, storage.getId());
                prepareStatement.setLong(2, file.getId());
                int res = prepareStatement.executeUpdate();

                System.out.println("File " + file.getName() + "." + file.getFormat() + " successfully been put to storage " + storage.getId() + " with a result " + res);

                conn.commit();
            } else throw new Exception("Something wrong. Please check your file and storage");


        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }

        System.out.println(file.toString());
        return file;
    }

    public List<File> putAll(Storage storage, List<File> files) throws Exception {


        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("UPDATE FILES SET STORAGE_ID = ?")) {

            conn.setAutoCommit(false);

            prepareStatement.setLong(1, storage.getId());

            if (validateFilesSize(files, storage)) {

                int res = prepareStatement.executeUpdate();

                for (File file : files) {
                    if (!fileInStorage(storage, file)) {
                        file.setStorage(storage);
                        System.out.println("File " + file.getName() + "." + file.getFormat() + " put to storage " + storage.getId() + " with result " + res);
                    } else {
                        throw new Exception("File " + file.getName() + "." + file.getFormat() + " is already in storage " + storage.getId() + ". Put failed");

                    }
                }


                conn.commit();

            } else conn.rollback();


        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }


        return files;
    }

    public void delete(Storage storage, File file) throws Exception {


        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("UPDATE FILES SET STORAGE_ID = ? WHERE ID = ?")) {

            conn.setAutoCommit(false);

            if (fileInStorage(storage, file)) {

                prepareStatement.setObject(1, null);
                prepareStatement.setLong(2, file.getId());
                file.setStorage(null);
                int res = prepareStatement.executeUpdate();
                conn.commit();
                System.out.println("File " + file.getName() + "." + file.getFormat() + " deleted from storage " + storage.getId() + " with result " + res);
                System.out.println(file.toString());

            }

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

            if (validateFilesSize(files, storageTo)) {
                prepareStatement.setLong(1, storageTo.getId());
                prepareStatement.setLong(2, storageFrom.getId());
                int res = prepareStatement.executeUpdate();
                for (File file : files) {

                    file.setStorage(storageTo);
                    System.out.println("File " + file.getName() + "." + file.getFormat() + " transferred to storage " + storageTo.getId());

                }


                conn.commit();

            } else {
                System.err.println("Not enough space in storage " + storageTo.getId());
                conn.rollback();
            }


        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }

        return files;
    }

    public File transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {


        File file = fileDAO.findById(id);


        if (!validateStorageSize(storageTo, file))
            throw new Exception("Not enough space for file " + file.getName() + "." + file.getFormat() + " in storage " + storageTo.getId());
        if (!fileInStorage(storageFrom, file))
            throw new Exception("There's no file " + file.getName() + "." + file.getFormat() + " in storage " + storageFrom.getId());
        else {

            file.setStorage(storageTo);
            System.out.println("File " + file.getName() + "." + file.getFormat() + " transferred to storage " + storageTo.getId());



        }


        return file;
    }


    private boolean validateStorageSize(Storage storage, File file) throws Exception {

        if (storage != null && file != null)
            return ((storage.getStorageMaxSize() - getFilesSize(storage)) > file.getSize());
        else if (storage == null) throw new Exception("There's no storage " + storage.getId());
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

    private boolean fileInStorage(Storage storage, File file) throws Exception {

        long databaseID = 0;

        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("SELECT STORAGE_ID FROM FILES WHERE ID = ?")) {

            conn.setAutoCommit(false);

            prepareStatement.setLong(1, file.getId());
            ResultSet rs = prepareStatement.executeQuery();

            while (rs.next()) {
                databaseID = rs.getLong(1);
            }

            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }


        if (storage == null) return false;
        if (file == null) return false;
        else
            return (databaseID == storage.getId() && file.getStorage().getId() == storage.getId());

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
