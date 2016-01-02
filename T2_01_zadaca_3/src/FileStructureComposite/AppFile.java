package FileStructureComposite;

import java.util.List;

/**
 * Created by lovro
 */
public interface AppFile {
      
    /**
     * Getters and setters for file elements
     * @return 
     */
    String getName();
    String getType();
    String getCreatedAt();
    String getUpdatedAt();
    String getSize();
    
    void setName(String name);
    void setType(String type);
    void setCreatedAt(String createdAt);
    void setUpdatedAt(String updatedAt);
    void setSize(String size);
    
    
    /**
     * File basic operations 
     * @param file
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
