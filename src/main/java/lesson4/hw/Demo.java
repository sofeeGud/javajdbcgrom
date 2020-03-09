package lesson4.hw;

import lesson4.hw.dao.FileDAO;
import lesson4.hw.dao.StorageDAO;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws Exception {
        String[] formatsArray = {"txt", "jpg"};

        Storage storage1 = new Storage(100, formatsArray, "Ukraine", 125);
        Storage storage2 = new Storage(200, formatsArray, "Turkey", 1000000000000L);
        Storage storage3 = new Storage(300, formatsArray, "Russia", 500000);

        File file1 = new File(1, "file1", "txt", 6465, storage1);
        File file2 = new File(2, "file2", "txt", 12999, storage3);
        File file3 = new File(3, "file3", "txt", 500, storage1);
        File file4 = new File(4, "file4", "txt", 100, storage2);
        File file5 = new File(5, "file5", "jpg", 6000, storage1);

        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file3);
        files.add(file4);
        files.add(file5);



        StorageDAO storageDAO = new StorageDAO();
        FileDAO fileDAO = new FileDAO();
        Controller controller = new Controller();

//        storageDAO.save(storage2);
//        storageDAO.save(storage3);
//        storageDAO.delete(111);
//        storageDAO.findById(112);
//        storageDAO.update(storage1);
 //       fileDAO.save(file5);
 //       fileDAO.save(file4);
 //       fileDAO.delete(4);
//      System.out.println(fileDAO.findById(1));
//        fileDAO.update(file2);
//        controller.transferFile(storage3, storage1, 1);
//        controller.transferFile(storage3, storage2, 4);
//        controller.put(storage2, file2);
//        System.out.println(file2.toString());
//        controller.delete(storage2, file3);
        controller.delete(storage3, file1);
//        controller.delete(storage1, file1);
 //       controller.transferFile(storage3, storage2, 1);
//        controller.transferFile(storage2, storage3, 2);
  //     controller.transferAll(storage1, storage2);
















    }
}
