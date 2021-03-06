/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompositeIterator;

import FileStructureComposite.AppFile;
import additional.layers.ReportLayer;
import additional.visitor.HashVisitor;
import java.util.ArrayList;

/**
 * Composite iterator for walking through file tree composite
 *
 * @author tonovosel
 */
public class FileTreeIterator implements Container {

    public ArrayList<String> elementsData = new ArrayList<>();
    public int numDir, numFil = 0;

    @Override
    public Iterator getIterator() {
        return new CreatedTreeIterator();
    }

    private class CreatedTreeIterator implements Iterator {

        private int index;

        @Override
        public boolean hasNext(AppFile parent) {
            int ChildrenNum = parent.getChildren().size();
            if (index < ChildrenNum) {
                return true;
            }
            return false;
        }

        @Override
        public Object getNextChild(AppFile parent) {
            if (this.hasNext(parent)) {
                return parent.getChild(index++);
            }
            return null;
        }

    }//inner class

    
    /**
     * Method that gets different extensions from file tree and saves them to array
     * @param elem 
     */
    public void getFileExtensions(AppFile elem) {

        FileTreeIterator ft = this;

        for (Iterator iter = ft.getIterator(); iter.hasNext(elem);) {
            AppFile nextElement = (AppFile) iter.getNextChild(elem);

            String type = nextElement.getType();

            if(!ReportLayer.fileTypes.contains(type)){
                ReportLayer.fileTypes.add(type);
            }

            if (nextElement.getType().equals("directory") && !nextElement.getChildren().isEmpty()) {
                getFileExtensions(nextElement);
            }
        }
    }
    /**
     * Method that compares extensions that is passed as first argument, and
     * adds elements with that extension to list
     * @param elem
     * @param extension 
     */
    public void compareExtensions(AppFile elem,String extension) {
        

        FileTreeIterator ft = this;
        for (Iterator iter = ft.getIterator(); iter.hasNext(elem);) {
            AppFile nextElement = (AppFile) iter.getNextChild(elem);
            String type = nextElement.getType();

            if (extension.equals(type)) {
                additional.FileInfo.elementCount++;
                additional.FileInfo.totalFileSize += nextElement.getRawSize();
                additional.FileInfo.extensionFiles.add(nextElement);

            if(extension.equals(type)){
                ReportLayer.elementCount++;
                ReportLayer.totalFileSize += nextElement.getRawSize();
                ReportLayer.extensionFiles.add(nextElement);

            }

            if (nextElement.getType().equals("directory") && !nextElement.getChildren().isEmpty()) {
                compareExtensions(nextElement, extension);
            }
        }
    }
    }

    /**
     * Method that returns elements data in list
     *
     * @param elem
     * @return
     */
    public ArrayList<String> getElementData(AppFile elem) {
        FileTreeIterator ft = this;
        for (Iterator iter = ft.getIterator(); iter.hasNext(elem);) {
            AppFile nextElement = (AppFile) iter.getNextChild(elem);
            elementsData.add("Name: " + nextElement.getName());
            elementsData.add("Type: " + nextElement.getType());
            elementsData.add("Created at: " + nextElement.getCreatedAt());
            elementsData.add("Updated at: " + nextElement.getUpdatedAt());
            elementsData.add("Size: " + nextElement.getFormattedSize());
            elementsData.add("-------------------");
            if (nextElement.getType().equals("directory") && !nextElement.getChildren().isEmpty()) {
                getElementData(nextElement);
            }
        }
        return elementsData;
    }

    public ArrayList<String> getNumberDirsAndFiles() {
        elementsData.clear();
        elementsData.add("Ukupan broj direktorija : " + (numDir + 1));
        elementsData.add("Ukupan broj datoteka : " + numFil);
        numDir = 0;
        numFil = 0;
        return elementsData;
    }

    public void clearData() {
        elementsData.clear();
    }

    public void calculateNumberOfDirsAndFiles(AppFile elem) {

        FileTreeIterator ft = this;
        for (Iterator iter = ft.getIterator(); iter.hasNext(elem);) {
            AppFile nextElement = (AppFile) iter.getNextChild(elem);
            if (nextElement.getType().equals("directory")) {
                numDir++;
            } else {
                numFil++;
            }
            if (nextElement.getType().equals("directory") && !nextElement.getChildren().isEmpty()) {
                calculateNumberOfDirsAndFiles(nextElement);
            }
        }
    }
    


    public void calculateHash(AppFile elem, HashVisitor hv){
        
        FileTreeIterator ft = this;
        for (Iterator iter = ft.getIterator(); iter.hasNext(elem);) {
            AppFile nextElement = (AppFile) iter.getNextChild(elem);
            nextElement.accept(hv);
            
            if (nextElement.getType().equals("directory") && !nextElement.getChildren().isEmpty()) {
                calculateHash(nextElement,hv);
            }
        }
    }

}
