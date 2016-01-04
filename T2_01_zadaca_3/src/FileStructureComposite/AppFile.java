package FileStructureComposite;

import additional.visitor.TreeElementInterface;
import java.util.List;

/**
 * Created by lovro
 */
public interface AppFile extends TreeElementInterface{
      
    /**
     * Getters and setters for file elements
     * @return 
     */
    String getName();
    String getType();
    String getCreatedAt();
    String getUpdatedAt();
    String getFormattedSize();
    long getRawSize();
    
    void setName(String name);
    void setType(String type);
    void setCreatedAt(String createdAt);
    void setUpdatedAt(String updatedAt);
    void setFormattedSize(String size);
    void setRawSize(long rawSize);
    
    
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

    List<AppFile> getParent();

    /**
     * Helper functions to manipulate structure
     */
    void print();
    
    void increaseSize(long size);
    
    // Required for memento
    
    void clearParentList();
    
    AppFile clone();
}