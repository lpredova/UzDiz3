/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompositeIterator;

import FileStructureComposite.AppFile;

/**
 * Composite iterator for walking through file tree composite
 * @author tonovosel
 */
public class FileTreeIterator implements Container {
    
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
    
}
