package FileReaderComposite;

import java.util.List;

/**
 * Created by lovro
 */
public interface File {

    void add(File file);

    List<File> getFiles();

    void remove(File file);

    File getChild(int i);

    void addChild(File file);

    List<File> getChildren();

    void addParent(File file);

    List<File> getParents();

    void print();
}
