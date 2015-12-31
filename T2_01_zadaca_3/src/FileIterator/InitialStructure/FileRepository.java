/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIterator.InitialStructure;

import FileStructureComposite.AppFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lovro
 */
public class FileRepository implements Container {

    public static List<AppFile> directoryTree = new ArrayList<AppFile>();

    @Override
    public Iterator getIterator() {
        return new InitialFileIterator();
    }

    private class InitialFileIterator implements Iterator {

        public InitialFileIterator() {
            if (directoryTree.isEmpty()) {
                this.createTree(t2_01_zadaca_3.T2_01_zadaca_3.rootDirectory);
            }
        }

        @Override
        public boolean hasNext() {
            return true; //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Object next() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private boolean fileExists(String path) {
            File f = new File(path);
            return f.exists();
        }

        private boolean isDirectory(String path) {
            File f = new File(path);
            return f.isDirectory();
        }

        private void createTree(String path) {

            //creating tree only in first run
            if (directoryTree.isEmpty()) {
                if (this.fileExists(path)) {

                    if (this.isDirectory(path)) {
                        //root element is directory
                        this.saveDirectoryInfo(path);
                    } else {
                        //root element is file
                        System.out.println("Root element HAS TO BE DIRECTORY!");

                    }

                } else {
                    System.out.println("Error getting tree element");
                }
            }
        }

        private void saveDirectoryInfo(String path) {
             //root element is visible by default, and doesn't overlap with anyone
            //AppFile rootElement = new Parent();  

            //TODO make saving root element to structure
            
            System.out.println("treee");
        }

        private void saveFileInfo(String path) {
        }

    }
}
