package jdbc.lesson4.hw;

import jdbc.lesson4.hw.dao.FileDAO;

import jdbc.lesson4.hw.dao.StorageDAO;

import java.util.List;
public class Service {
    private FileDAO fileDAO = new FileDAO();
    private StorageDAO storageDAO = new StorageDAO();


    public File put(Storage storage, File file) throws Exception {
        if (fileDAO.validateStorageSize(storage, file)) {
            fileDAO.put(storage, file);
            file.setStorage(storage);
        } else {
            System.err.println("Not enough space in storage " + storage.getId());

        }
        return file;
    }

    public List<File> putAll(Storage storage, List<File> files) throws Exception {

        for (File file : files) {
            if (file != null && fileDAO.validateStorageSize(storage, file)) {
                fileDAO.put(storage, file);
                file.setStorage(storage);
            } else {
                System.err.println("Not enough space in storage " + storage.getId());

            }
        }
        return files;
    }

    public List<File> transferAll(Storage storageFrom, Storage storageTo) throws Exception {

        List<File> filesFrom = fileDAO.getFiles(storageFrom);

        for (File file : filesFrom) {
            if (fileDAO.validateStorageSize(storageTo, file)) {
                fileDAO.put(storageTo, file);
                file.setStorage(storageTo);
            } else {
                System.err.println("Not enough space in storage " + storageTo.getId());

            }
        }
        return filesFrom;
    }

    public File transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {

        File file = fileDAO.findById(id);

        if (fileDAO.validateStorageSize(storageTo, file)) {
            fileDAO.put(storageTo, file);
            file.setStorage(storageTo);
        } else {
            throw new Exception("Not enough space for file " + file.getName() + "." + file.getFormat() + " in storage " + storageTo.getId());
        }
        return file;
    }

    public void delete(Storage storage, File file) throws Exception {
        if (file != null && storage != null) {
            fileDAO.delete(file.getId());
            storageDAO.delete(storage.getId());
        }
    }
}
