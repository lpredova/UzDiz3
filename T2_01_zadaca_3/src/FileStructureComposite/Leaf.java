package FileStructureComposite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lovro
 */
public class Leaf implements AppFile {

    private String name;
    private String type;
    private String createdAt;
    private String updatedAt;
    private String size;

    private List<AppFile> parentFiles = new ArrayList<>();
    private List<AppFile> files = new ArrayList<>();

    public Leaf(String name, String type, String createdAt, String updatedAt, String size) {
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.size = size;
    }

    @Override
    public void add(AppFile shape) {
    }

    @Override
    public List<AppFile> getFiles() {
        return files;
    }

    @Override
    public void remove(AppFile shape) {
    }

    @Override
    public AppFile getChild(int i) {
        return null;
    }

    @Override
    public void addChild(AppFile shape) {
    }

    @Override
    public void addParent(AppFile parent) {
        parentFiles.add(parent);
    }

    @Override
    public List<AppFile> getParent() {
        return null;
    }

    @Override
    public List<AppFile> getChildren() {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public String getSize() {
        return this.size;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void print() {

        System.out.println("Name:" + this.getName());
        System.out.println("Type:" + this.getType());
        System.out.println("Created at:" + this.getCreatedAt());
        System.out.println("Updated at:" + this.getUpdatedAt());
        System.out.println("Size:" + this.getSize());

        System.out.println("------------------------------------");
    }
}
