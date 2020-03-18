package jdbc.lesson4.hw;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
 public class File {
    private long id;
    private String name;
    private String format;
    private long size;
    private Storage storage;


    public File(long id, String name, String format, long size, Storage storage) {
        this.id = id;
        this.name = name;
        this.format = format;
        this.size = size;
        this.storage = storage;
    }

}
