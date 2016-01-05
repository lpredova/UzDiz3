package FileStructureComposite;


import additional.visitor.TreeElementVisitor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lovro
 */
public class Leaf implements AppFile,TreeElementVisitor {

    private String name;
    private String type;
    private String createdAt;
    private String updatedAt;
    private String formattedSize;
    private String fileHash;
    private long rawSize;

    private List<AppFile> parentFiles = new ArrayList<>();
    private List<AppFile> files = new ArrayList<>();

    public Leaf(String name, String type, String createdAt, String updatedAt, String size, long rawSize) {
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.formattedSize = size;
        this.rawSize = rawSize;
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
    public void clearParentList(){
        this.parentFiles.clear();
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
    public String getFormattedSize() {
        return this.formattedSize;
    }

    @Override
    public long getRawSize() {
        return this.rawSize;
    }
    
    @Override
    public String getFileHash() {
        return this.fileHash;
    }

    @Override
    public void setFileHash(String hash) {
        this.fileHash = hash;
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
    public void setFormattedSize(String size) {
        this.formattedSize = size;
    }

    @Override
    public void setRawSize(long rawSize) {
        this.rawSize = rawSize;
    }

    @Override
    public void print() {

        System.out.println("Name:" + this.getName());
        System.out.println("Type:" + this.getType());
        System.out.println("Created at:" + this.getCreatedAt());
        System.out.println("Updated at:" + this.getUpdatedAt());
        System.out.println("Size:" + this.getFormattedSize());

        System.out.println("------------------------------------");
    }
    
    /**
     * Method that returns data from element as string
     *
     * @return
     */
    @Override
    public String elementData() {

        String output;
        output
                = "\nName:" + this.getName() + "\n"
                + "Type:" + this.getType() + "\n"
                + "Created at:" + this.getCreatedAt() + "\n"
                + "Updated at:" + this.getUpdatedAt() + "\n"
                + "Size:" + this.getFormattedSize() + "\n"
                + "Hash: " + this.getFileHash() + "\n"
                + "------------------------------------";

        return output;
    }

    /**
     * Method that we use for updating parents size, not really necessary with
     * child nodes
     *
     * @param size
     */
    @Override
    public void increaseSize(long size) {
    }

    @Override
    public Leaf clone() {
        Leaf clone = new Leaf(name, type, createdAt, updatedAt, name, rawSize);

        clone.formattedSize = this.formattedSize;
        clone.parentFiles.add(clone);

        for (AppFile child : files) {
            AppFile childClone = (AppFile) child.clone();

            childClone.clearParentList();
            childClone.addParent(clone);

            clone.addChild(childClone);
        }

        return clone;
    }
    
    @Override
    public void visit(AppFile file) {
        //   
    } 
    
    @Override
    public void accept(TreeElementVisitor elementVisitor) {
        elementVisitor.visit(this);
    }
    
    @Override
    public String getAbsolutePath(){
        // If this is root
        if(parentFiles.get(0) == null)
            return this.name;
        
        return parentFiles.get(0).createAbsolutePath(this.name);
    }
    
    @Override
    public String createAbsolutePath(String childPath){
        String absolutePath = this.name + "\\" + childPath;
        
        if(parentFiles.get(0) == null)
            return absolutePath;
        
        parentFiles.get(0).createAbsolutePath(absolutePath);
        
        return "Error!";
    }
}
