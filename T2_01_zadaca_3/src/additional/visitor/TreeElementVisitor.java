/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package additional.visitor;

import FileStructureComposite.AppFile;

/**
 *
 * @author lovro
 */
public interface TreeElementVisitor {
    	public void visit(AppFile file);

}
