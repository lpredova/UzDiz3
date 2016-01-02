/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIterator.InitialStructure;

import FileStructureComposite.AppFile;
import FileStructureComposite.Parent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lovro
 */
public class FileRepository implements Container {

    public static List<AppFile> directoryTree = new ArrayList<AppFile>();

    @Override
    public Iterator getIterator(String root) {
        return new InitialFileIterator(root);
    }

    private class InitialFileIterator implements Iterator {

        public InitialFileIterator(String root) {
            if (directoryTree.isEmpty()) {
                this.createTree(root);
            }
        }

        @Override
        public boolean hasNext() {
            if (nextElementExists()) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            return null;
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

            AppFile rootElement = new Parent(getFileName(path), getFileType(path), getFileCreatedAtTime(path), getFileUpdatedAtTime(path), getFileSize(path));

            //TODO make saving root element to structure
            System.out.println("treee");
        }

        private void saveFileInfo(String path) {
        }

        private boolean nextElementExists() {

            return true;
        }

        /**
         * Method for getting file name from path
         *
         * @param path
         * @return
         */
        private String getFileName(String path) {
            File f = new File(path);
            return f.getName();
        }
        
        private long getFileSize(String path){
            File f = new File(path);
            return f.length();
        }

        /**
         * Method for getting file type, returns directory if file is directory
         * or extension otherwise
         *
         * @param path
         * @return
         */
        private String getFileType(String path) {
            if (isDirectory(path)) {
                return "directory";
            }
            File f = new File(path);
            String name = f.getName();
            try {
                return name.substring(name.lastIndexOf(".") + 1) + " file";
            } catch (Exception e) {
                return "file";
            }
        }

        /**
         * Method for getting time file is created at
         *
         * @return
         */
        private String getFileCreatedAtTime(String path) {

            File file = new File(path);
            Path filePath = file.toPath();

            try {
                BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);

                long milliseconds = fileAttributes.creationTime().to(TimeUnit.MILLISECONDS);
                if ((milliseconds > Long.MIN_VALUE) && (milliseconds < Long.MAX_VALUE)) {
                    Date creationDate = new Date(fileAttributes.creationTime().to(TimeUnit.MILLISECONDS));
                    return creationDate.getDate() + "." + (creationDate.getMonth() + 1) + "." + (creationDate.getYear() + 1900);
                }

            } catch (IOException ex) {
                Logger.getLogger(FileRepository.class.getName()).log(Level.SEVERE, null, ex);
                return "Unable to fetch time";
            }
            return "1.1.2016";    
        }
        
        /**
         * Method for getting last updated time of the file
         * @param path
         * @return 
         */
        private String getFileUpdatedAtTime(String path) {

            File file = new File(path);
            Path filePath = file.toPath();

            try {
                BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);

                long milliseconds = fileAttributes.creationTime().to(TimeUnit.MILLISECONDS);
                if ((milliseconds > Long.MIN_VALUE) && (milliseconds < Long.MAX_VALUE)) {
                    Date updatedDate = new Date(fileAttributes.lastModifiedTime().to(TimeUnit.MILLISECONDS));
                    return updatedDate.getDate() + "." + (updatedDate.getMonth() + 1) + "." + (updatedDate.getYear() + 1900);
                }
            } catch (IOException ex) {
                Logger.getLogger(FileRepository.class.getName()).log(Level.SEVERE, null, ex);
                return "Unable to fetch time";
            }
            return "1.1.2016";    
        }
    }
}
