package lesson4.hw.dao;
import lesson4.hw.Storage;

import java.sql.*;

public class StorageDAO implements GenDAO<Storage> {
    @Override
    public Storage save(Storage storage) {

        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("INSERT INTO STORAGE VALUES(?, ?, ?, ?)")) {


            prepareStatement.setLong(1, storage.getId());
            prepareStatement.setString(2, convertFormats(storage.getFormatsSupported()));
            prepareStatement.setString(3, storage.getStorageCountry());
            prepareStatement.setLong(4, storage.getStorageMaxSize());

            int res = prepareStatement.executeUpdate();

            System.out.println("Saved with result " + res);


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return storage;
    }

    @Override
    public void delete(long id) {

        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("DELETE FROM STORAGE WHERE ID = ?")) {

            prepareStatement.setLong(1, id);


            ValidateId(prepareStatement, id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Storage update(Storage storage) {

        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("UPDATE STORAGE SET STORAGE_COUNTRY = ?, STORAGE_SIZE = ? WHERE ID = ?")) {

            prepareStatement.setString(1, storage.getStorageCountry());
            prepareStatement.setLong(2, storage.getStorageMaxSize());
            prepareStatement.setLong(3, storage.getId());

            ValidateId(prepareStatement, storage.getId());


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storage;
    }

    public Storage findById(long id) {

        try (Connection conn = getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM STORAGE WHERE ID = ?")) {

            prepareStatement.setLong(1, id);

            ResultSet rs = prepareStatement.executeQuery();


            while (rs.next()) {


                String[] formats = rs.getString(2).split(",");
                Storage storage = new Storage(rs.getLong(1), formats, rs.getString(3), rs.getLong(4));

                System.out.println(storage.toString());
                ValidateId(prepareStatement, id);


                return storage;

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private String convertFormats(String[] formatsSupported) {

        StringBuffer stringBuffer = new StringBuffer();

        for (String format : formatsSupported) {
            stringBuffer.append(format + ", ");
        }
        stringBuffer.deleteCharAt(stringBuffer.lastIndexOf(", "));
        String formats = stringBuffer.toString();


        return formats;
    }

    private void ValidateId(PreparedStatement preparedStatement, long id) throws SQLException {
        int res = preparedStatement.executeUpdate();
        if (res == 0) System.err.println("There is no ID= " + id);
        else System.out.println("Process finished with result " + res);
    }
}
