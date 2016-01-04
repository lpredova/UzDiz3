/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompositeIterator;

import FileStructureComposite.AppFile;
import java.util.ArrayList;

/**
 * Composite iterator for walking through file tree composite
 *
 * @author tonovosel
 */
public class FileTreeIterator implements Container {

    ArrayList<String> elementsData = new ArrayList<>();

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
     * Recursion for printing out file tree composite
     *
     * @param elem
     */
    public void printStructure(AppFile elem) {

        FileTreeIterator ft = this;
        for (Iterator iter = ft.getIterator(); iter.hasNext(elem);) {
            AppFile nextElement = (AppFile) iter.getNextChild(elem);
            nextElement.print();
            if (nextElement.getType().equals("directory") && !nextElement.getChildren().isEmpty()) {
                printStructure(nextElement);
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

}
