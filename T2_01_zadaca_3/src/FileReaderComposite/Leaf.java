package FileReaderComposite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lovro
 */
public class Leaf implements File {

    private String name;
    private List<File> parentFiles = new ArrayList<File>();
    private List<File> files = new ArrayList<File>();


    public Leaf(String name) {
        this.name = name;
    }

    @Override
    public void add(File shape) {
    }

    @Override
    public List<File> getFiles() {
        return files;
    }

    @Override
    public void remove(File shape) {
    }

    @Override
    public File getChild(int i) {
        return null;
    }

    @Override
    public void addChild(File shape) {}

    @Override
    public void addParent(File parent) {
        parentFiles.add(parent);
    }

    @Override
    public List<File> getParents() {
        return null;
    }

    @Override
    public List<File> getChildren() {
        return null;
    }

    @Override
    public void print() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
