/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIterator.InitialStructure;

import FileStructureComposite.AppFile;
import FileStructureComposite.Leaf;
import FileStructureComposite.Parent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lovro
 */
public class FileRepository implements Container {

    public static List<AppFile> directoryTree = new ArrayList<AppFile>();

    @Override
    public Iterator getIterator(String root) {
        return new InitialFileIterator(root);
    }

    private class InitialFileIterator implements Iterator {

        String elementPath;
        int index = 0;

        public InitialFileIterator(String root) {
            if (directoryTree.isEmpty()) {
                this.createTree(root);
            }
        }

        @Override
        public boolean hasNext() {
            boolean var = nextElementExists();
            System.out.println(var);
            return var;
        }

        @Override
        public Object next() {
            return directoryTree.get(index++);
        }

        /**
         * Method for creating root of directory tree, root must be directory,
         * otherwise we print out error and stop the program
         *
         * @param path
         */
        private void createTree(String path) {

            //creating tree only in first run
            if (directoryTree.isEmpty()) {
                if (Helpers.FileHelper.fileExists(path)) {
                    if (Helpers.FileHelper.isDirectory(path)) {
                        //root element is directory
                        this.elementPath = path;
                        
                        AppFile rootDirectoryElement = new Parent(Helpers.FileHelper.getFileNameFromPath(path),
                    Helpers.FileHelper.getFileTypeFromPath(path), Helpers.FileHelper.getFileCreatedAtTimeFromPath(path),
                    Helpers.FileHelper.getFileUpdatedAtTimeFromPath(path), Helpers.FileHelper.getFileSizeFromPath(path));

            directoryTree.add(rootDirectoryElement);
                        
                        
                        
                        System.out.println("Created tree root");
                    } else {
                        //root element is file
                        System.out.println("Root element HAS TO BE DIRECTORY!");
                        System.exit(0);
                    }
                } else {
                    System.out.println("Error getting tree element");
                }
            }
        }

        /**
         * Method that gets basic info of current element, creates object and
         * saves it to structure, every directory can contain another directory
         * creating the tree so it is parent element
         *
         * @param path
         */
        private void saveDirectoryInfo(File directory) {
            AppFile directoryElement = new Parent(Helpers.FileHelper.getFileName(directory),
                    Helpers.FileHelper.getFileType(directory), Helpers.FileHelper.getFileCreatedAtTime(directory),
                    Helpers.FileHelper.getFileUpdatedAtTime(directory), Helpers.FileHelper.getFileSize(directory));

            directoryTree.add(directoryElement);
        }

        /**
         * Child elements are always leafs
         *
         * @param path
         */
        private void saveFileInfo(File file) {
            AppFile fileElement = new Leaf(Helpers.FileHelper.getFileName(file),
                    Helpers.FileHelper.getFileType(file), Helpers.FileHelper.getFileCreatedAtTime(file),
                    Helpers.FileHelper.getFileUpdatedAtTime(file), Helpers.FileHelper.getFileSize(file));

            directoryTree.add(fileElement);
        }

        /**
         * Method that checks if next element in current structure exists
         * regardless if element is directory or file
         *
         * @return
         */
        private boolean nextElementExists() {

            File[] files = new File(this.elementPath).listFiles();
            showFiles(files);
            return false;
        }

        public void showFiles(File[] files) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                    this.saveDirectoryInfo(file);
                    showFiles(file.listFiles());
                } else {
                    this.saveFileInfo(file);
                    System.out.println("File: " + file.getName());
                }
            }
        }
    }
}