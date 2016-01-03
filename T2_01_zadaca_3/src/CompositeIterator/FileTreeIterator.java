/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompositeIterator;

import FileIterator.InitialStructure.FileRepository;
import FileStructureComposite.AppFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tonovosel
 */
public class FileTreeIterator implements Container {

    private List<AppFile> directoryTree = new ArrayList<AppFile>();

    @Override
    public Iterator getIterator() {
        return new CreatedTreeIterator();
    }

    private class CreatedTreeIterator implements Iterator {

        private int index;
        private AppFile root;

        public CreatedTreeIterator() {
            directoryTree = FileRepository.directoryTree;
            root = directoryTree.get(0);
        }

        @Override
        public boolean hasNext(AppFile parent) {
            int ChildrenNum = parent.getChildren().size();
            if (index < ChildrenNum) {
                return true;
            }
            return false;
        }

        @Override
        public Object getNextChild() {
            if (this.hasNext(root)) {
                return directoryTree.get(0).getChild(index++);
            }
            return null;
        }
    }

}
