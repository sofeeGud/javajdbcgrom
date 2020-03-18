package jdbc.lesson4.hw;

import java.util.List;

public class Controller {

    private Service service = new Service();

    public File put(Storage storage, File file) throws Exception {
        return service.put(storage, file);
    }

    public List<File> putAll(Storage storage, List<File> files) throws Exception {
        return service.putAll(storage, files);
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        service.transferAll(storageFrom, storageTo);
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        service.transferFile(storageFrom, storageTo, id);
    }

    public void delete(Storage storage, File file) throws Exception {
        service.delete(storage, file);
    }
}
