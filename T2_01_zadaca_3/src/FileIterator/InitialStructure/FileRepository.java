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
        /**
         * This method is false by default because there is only one element in
         * root, we add that element and recursions do the rest, In fact we
         * don't need separator for this but let it be
         */
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            return null;
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
                if (helpers.FileHelper.fileExists(path)) {
                    if (helpers.FileHelper.isDirectory(path)) {
                        //root element is directory
                        this.elementPath = path;

                        AppFile rootDirectoryElement = new Parent(
                                helpers.FileHelper.getFileNameFromPath(path),
                                helpers.FileHelper.getFileTypeFromPath(path),
                                helpers.FileHelper.getFileCreatedAtTimeFromPath(path),
                                helpers.FileHelper.getFileUpdatedAtTimeFromPath(path),
                                helpers.FileHelper.getFileSizeFormattedFromPath(path),
                                helpers.FileHelper.getFileRawSizeFromPath(path)
                        );
                        rootDirectoryElement.addParent(null);
                        directoryTree.add(rootDirectoryElement);

                        /**
                         * Recursion that created directory tree
                         */
                        File[] files = new File(path).listFiles();
                        showFiles(files);
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

            AppFile directoryElement = new Parent(
                    helpers.FileHelper.getFileName(directory),
                    "directory",
                    helpers.FileHelper.getFileCreatedAtTime(directory),
                    helpers.FileHelper.getFileUpdatedAtTime(directory),
                    helpers.FileHelper.getFileFormattedSize(directory),
                    helpers.FileHelper.getFileRawSize(directory));

            long elementSize = helpers.FileHelper.getFileRawSize(directory);

            AppFile parentElement = findParent(directory, elementSize);
            directoryElement.addParent(parentElement);
            parentElement.addChild(directoryElement);
            directoryTree.add(directoryElement);
        }

        /**
         * Child elements are always leafs
         *
         * @param path
         */
        private void saveFileInfo(File file) {

            AppFile fileElement = new Leaf(
                    helpers.FileHelper.getFileName(file),
                    helpers.FileHelper.getFileType(file),
                    helpers.FileHelper.getFileCreatedAtTime(file),
                    helpers.FileHelper.getFileUpdatedAtTime(file),
                    helpers.FileHelper.getFileFormattedSize(file),
                    helpers.FileHelper.getFileRawSize(file));
            long fileSize = helpers.FileHelper.getFileRawSize(file);

            AppFile parentElement = findParent(file, fileSize);
            fileElement.addParent(parentElement);
            parentElement.addChild(fileElement);
            directoryTree.add(fileElement);
        }

        /**
         * Method that checks if next element in current structure exists
         * regardless if element is directory or file
         *
         * @return
         */
        private boolean nextElementExists() {
            return false;
        }

        /**
         * Recursion that iterates trough elements in file structure
         *
         * @param files
         */
        private void showFiles(File[] files) {
            for (File file : files) {
                if (file.isDirectory()) {
                    this.saveDirectoryInfo(file);
                    showFiles(file.listFiles());
                } else {
                    this.saveFileInfo(file);
                }
            }
        }

        /**
         * Recursion for finding element parent
         *
         * @param file
         * @return
         */
        private AppFile findParent(File file, long size) {

            String parentName = file.getParentFile().getName();
            for (AppFile appFile : directoryTree) {
                if (appFile.getName().equals(parentName)) {
                    appFile.increaseSize(size);
                    return appFile;
                }

                //if dir name is not the same and element is dir, enter recursion
                if (appFile.getType().equals("directory")) {
                    appFile.increaseSize(size);

                    AppFile result = getParentElement(appFile, parentName, size);
                    if (result != null) {
                        return result;
                    }
                }
            }
            return null;
        }

        private AppFile getParentElement(AppFile file, String parentName, long size) {

            for (AppFile child : file.getChildren()) {
                //Anchor
                if (child.getName().equals(parentName)) {
                    child.increaseSize(size);
                    return child;
                }
                //ok, not on this level, maybe below?
                //element is container
                if (child.getType().equals("directory")) {
                    child.increaseSize(size);
                    return this.getParentElement(child, parentName, size);
                }
            }
            return null;
        }
    }
}
