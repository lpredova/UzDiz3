package FileStructureComposite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lovro
 */
public class Parent implements AppFile {

    private String name;
    private String type;
    private String createdAt;
    private String updatedAt;
    private long size;
    
    private List<AppFile> parentFiles = new ArrayList<AppFile>();
    private List<AppFile> files = new ArrayList<AppFile>();

    public Parent(String name,String type,String createdAt,String updatedAt,long size) {
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.size = size;
    }

    @Override
    public void add(AppFile file) {
        files.add(file);
    }

    @Override
    public List<AppFile> getFiles() {
        return this.files;
    }

    @Override
    public void remove(AppFile shape) {
    }

    @Override
    public AppFile getChild(int i) {
        return files.get(i);
    }
 
    @Override
    public void addChild(AppFile file) {
        files.add(file);
    }

    @Override
    public List<AppFile> getChildren() {
        return this.files;
    }

    @Override
    public void addParent(AppFile file) {
        parentFiles.add(file);
    }

    @Override
    public List<AppFile> getParents() {
        return this.parentFiles;
    }

    @Override
    public void print() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
