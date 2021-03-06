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
    String getFileHash();
    long getRawSize();
    boolean getIsRoot();
    String getRootAbsoluteAddress();
    String getParentName();
    
    void setName(String name);
    void setType(String type);
    void setCreatedAt(String createdAt);
    void setUpdatedAt(String updatedAt);
    void setFormattedSize(String size);
    void setFileHash(String hash);
    void setRawSize(long rawSize);
    void setIsRoot(boolean isRoot);
    void setRootAbsouluteAddress(String absAddress);
    void setParentName(String parentName);
    
    
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
    
    String elementData();
    
    void increaseSize(long size);
    
    // Required for memento
    
    void clearParentList();
    
    AppFile clone();
}