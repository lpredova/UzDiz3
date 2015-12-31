package FileStructureComposite;

import java.util.List;

/**
 * Created by lovro
 */
public interface AppFile {

    void add(AppFile file);

    List<AppFile> getFiles();

    void remove(AppFile file);

    AppFile getChild(int i);

    void addChild(AppFile file);

    List<AppFile> getChildren();

    void addParent(AppFile file);

    List<AppFile> getParents();

    void print();
}
