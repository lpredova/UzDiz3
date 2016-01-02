package FileStructureComposite;

import java.util.List;

/**
 * Created by lovro
 */
public interface AppFile {
      
    /**
     * Getters and setters for file elements
     */
    String getName();
    String getType();
    String getCreatedAt();
    String getUpdatedAt();
    long getSize();
    
    void setName(String name);
    void setType(String type);
    void setCreatedAt(String createdAt);
    void setUpdatedAt(String updatedAt);
    void setSize(long size);
    
    
    /**
     * File basic operations 
     */
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
