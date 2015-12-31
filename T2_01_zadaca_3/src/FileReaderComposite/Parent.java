package FileReaderComposite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lovro
 */
public class Parent implements File {

    private String name;
    private List<File> parentFiles = new ArrayList<File>();
    private List<File> files = new ArrayList<File>();

    public Parent(String name) {
        this.name = name;
    }

    @Override
    public void add(File file) {
        files.add(file);
    }

    @Override
    public List<File> getFiles() {
        return this.files;
    }

    @Override
    public void remove(File shape) {
        files.remove(shape);
    }

    @Override
    public File getChild(int i) {
        return files.get(i);
    }
 
    @Override
    public void addChild(File file) {
        files.add(file);
    }

    @Override
    public List<File> getChildren() {
        return this.files;
    }

    @Override
    public void addParent(File file) {
        parentFiles.add(file);
    }

    @Override
    public List<File> getParents() {
        return this.parentFiles;
    }

    @Override
    public void print() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
