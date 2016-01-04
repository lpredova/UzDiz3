/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompositeIterator;

import FileStructureComposite.AppFile;

/**
 *
 * @author tonovosel
 */
public interface Iterator {

    public boolean hasNext(AppFile parent);

    public Object getNextChild(AppFile parent);
}
